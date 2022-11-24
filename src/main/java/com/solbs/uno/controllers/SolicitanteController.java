package com.solbs.uno.controllers;

import com.solbs.uno.dtos.SolicitanteDto;
import com.solbs.uno.entities.Solicitante;
import com.solbs.uno.services.SolicitanteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/solicitante")
@CrossOrigin("*")
public class SolicitanteController {
    @Autowired
    SolicitanteService solicitanteService;

    /**
     * Método HTTP que cadastra um solicitante na base de dados
     * @param solicitanteDto Dados do solicitante que será cadastrado
     * @return Entidade de resposta com mensagem de conflito com CNPJ cadastrado ou Entidade de resposta com o solicitante cadastrado
     */
    @PreAuthorize("hasAnyAuthority('ADMIN', 'VENDEDOR')")
    @PostMapping
    public ResponseEntity<Object> cadastrarSolicitante(@RequestBody @Valid SolicitanteDto solicitanteDto){
        if (solicitanteService.existsByCnpj(solicitanteDto.getCnpj())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CNPJ já cadastrado");
        }

        Solicitante solicitante = new Solicitante();
        BeanUtils.copyProperties(solicitanteDto, solicitante);
        return ResponseEntity.status(HttpStatus.CREATED).body(solicitanteService.salvarSolicitante(solicitante));
    }

    /**
     * Método HTTP que retorna todos os solicitantes cadastrados
     * @return Lista com todos Solicitantes
     */
    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<List<Solicitante>> retornarTodosSolicitantes(){
        List<Solicitante> lista = solicitanteService.procurarTodosSolicitantes();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    /**
     * Método HTTP que retorna um solicitante a partir de seu cnpj
     * @param cnpj CNPJ que será buscado na base de dados
     * @return Solicitante
     */
    @PreAuthorize("permitAll()")
    @GetMapping("/{cnpj}")
    public ResponseEntity<Solicitante> retornarSolicitantePorCnpj(@PathVariable String cnpj){
        return ResponseEntity.status(HttpStatus.OK).body(solicitanteService.procurarSolicitantePeloCnpj(cnpj));
    }

    /**
     * Método HTTP que atualiza dados cadastrais de um solicitante
     * @param cnpj CNPJ do solicitante que terá dados atualizados
     * @param dados Dados que serão atualizados no solicitante
     * @return Entidade de resposta HTTP
     */
    @PreAuthorize("hasAnyAuthority('ADMIN', 'VENDEDOR')")
    @PutMapping("/{cnpj}")
    public ResponseEntity<Object> atualizarSolicitante(@PathVariable String cnpj, @RequestBody SolicitanteDto dados) {
        Solicitante solicitante = solicitanteService.procurarSolicitantePeloCnpj(cnpj);
        solicitante = solicitanteService.atualizarDadosSolicitante(solicitante, dados);
        return ResponseEntity.status(HttpStatus.OK).body(solicitanteService.salvarSolicitante(solicitante));
    }
}


