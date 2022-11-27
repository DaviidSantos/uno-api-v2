package com.solbs.uno.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solbs.uno.dtos.UsuarioDto;
import com.solbs.uno.entities.Cargo;
import com.solbs.uno.entities.Usuario;
import com.solbs.uno.entities.enums.Cargos;
import com.solbs.uno.services.UsuarioService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/usuario")
@CrossOrigin("*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Método HTTP que cadastra um usuário na base de dados
     * @param usuarioDto Dados do usuário que será cadastrado
     * @return Entidade de Resposta com Usuário cadastrado
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Object> cadastrarUsuario(@RequestBody UsuarioDto usuarioDto){
        if (usuarioService.existsByEmail(usuarioDto.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDto, usuario);
        Cargo cargo = usuarioService.procurarCargo(usuarioDto.getCargo());
        usuario.setCargo(cargo);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvarUsuario(usuario));
    }

    /**
     * Método HTTP que retorna todos os usuários cadastrados na base de dados
     * @return Entidade de Resposta com Lista de Usuários Cadastrados
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Usuario>> retornarTodosUsuarios(){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.procurarTodosUsuarios());
    }

    /**
     * Método HTTP que retora um usuário a partir de seu id
     * @param idUsuario Id do Usuário que será retornado
     * @return Entidade de Resposta com Usuário
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{idUsuario}")
    public ResponseEntity<Usuario> retornarUsuarioPeloId(@PathVariable String idUsuario){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.procurarUsuarioPorId(idUsuario));
    }

    /**
     * Método HTTP que deleta um usuário da base de dados
     * @param idUsuario Id do Usuário que será deletado
     * @return Entidade de Resposta
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Object> deletarUsuario(@PathVariable String idUsuario){
        Usuario usuario = usuarioService.procurarUsuarioPorId(idUsuario);
        usuarioService.deletarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário Deletado com Sucesso!");
    }

    /**
     * Método HTTP que altera a senha de um usuário
     * @param novaSenha Nova senha do usuário
     * @param idUsuario Id do usuário cuja senha será alteraa
     * @return Entidade de Resposta com usuário atualizado
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{idUsuario}")
    public ResponseEntity<Usuario> alterarSenha(@RequestBody String novaSenha, @PathVariable String idUsuario){
        Usuario usuario = usuarioService.procurarUsuarioPorId(idUsuario);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.alterarSenha(usuario, novaSenha));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                Usuario user = usuarioService.procurarUsuarioPorEmail(username);

                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 8 * 60 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e){
                response.setHeader("erro", e.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        }
        else{
            throw new RuntimeException("Refresh Token não encontrado");
        }
    }

}
