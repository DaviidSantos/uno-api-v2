package com.solbs.uno.controllers;

import com.solbs.uno.dtos.UsuarioDto;
import com.solbs.uno.entities.Usuario;
import com.solbs.uno.services.UsuarioService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody UsuarioDto usuarioDto){
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDto, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvarUsuario(usuario));
    }

    /**
     * Método HTTP que retorna todos os usuários cadastrados na base de dados
     * @return Entidade de Resposta com Lista de Usuários Cadastrados
     */
    @GetMapping
    public ResponseEntity<List<Usuario>> retornarTodosUsuarios(){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.procurarTodosUsuarios());
    }

    /**
     * Método HTTP que retora um usuário a partir de seu id
     * @param idUsuario Id do Usuário que será retornado
     * @return Entidade de Resposta com Usuário
     */
    @GetMapping("/{idUsuario}")
    public ResponseEntity<Usuario> retornarUsuarioPeloId(@PathVariable String idUsuario){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.procurarUsuarioPorId(idUsuario));
    }



    /**
     * Método HTTP que deleta um usuário da base de dados
     * @param idUsuario Id do Usuário que será deletado
     * @return Entidade de Resposta
     */
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
    @PostMapping("/{idUsuario}")
    public ResponseEntity<Usuario> alterarSenha(@RequestBody String novaSenha, @PathVariable String idUsuario){
        Usuario usuario = usuarioService.procurarUsuarioPorId(idUsuario);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.alterarSenha(usuario, novaSenha));
    }
}
