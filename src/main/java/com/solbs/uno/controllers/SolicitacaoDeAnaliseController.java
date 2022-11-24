package com.solbs.uno.controllers;

import com.solbs.uno.dtos.SolicitacaoDeAnaliseDto;
import com.solbs.uno.entities.SolicitacaoDeAnalise;
import com.solbs.uno.entities.Solicitante;
import com.solbs.uno.services.SolicitacaoDeAnaliseService;
import com.solbs.uno.services.SolicitanteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solicitacao-de-analise")
@CrossOrigin("*")
public class SolicitacaoDeAnaliseController {
    @Autowired
    private SolicitacaoDeAnaliseService solicitacaoDeAnaliseService;

    @Autowired
    private SolicitanteService solicitanteService;

    /**
     * Método HTTP que cadastra uma Solicitação de Análise na base de dados
     * @param solicitacaoDeAnaliseDto Dados da Solicitação de Análise a ser cadastrada
     * @return Entidade de resposta com a solicitação de análise cadastrada
     */
    @PostMapping
    public ResponseEntity<SolicitacaoDeAnalise> cadastrarSolicitacaoDeAnalise(@RequestBody SolicitacaoDeAnaliseDto solicitacaoDeAnaliseDto){
        SolicitacaoDeAnalise solicitacaoDeAnalise = new SolicitacaoDeAnalise();
        BeanUtils.copyProperties(solicitacaoDeAnaliseDto, solicitacaoDeAnalise);
        Solicitante solicitante = new Solicitante();
        solicitante = solicitanteService.procurarSolicitantePeloCnpj(solicitacaoDeAnaliseDto.getCnpj());
        solicitacaoDeAnalise.setSolicitante(solicitante);
        return ResponseEntity.status(HttpStatus.CREATED).body(solicitacaoDeAnaliseService.salvarSolicitacaoDeAnalise(solicitacaoDeAnalise));
    }

    /**
     * Método HTTP que retorna uma lista de Solicitações de Análise
     * @return Entidade de resposta com a lista de Solicitações de Análise
     */
    @GetMapping
    public ResponseEntity<List<SolicitacaoDeAnalise>> retornarTodosSolicitacaoDeAnalises(){
        List<SolicitacaoDeAnalise> lista = solicitacaoDeAnaliseService.procurarTodasSolicitacoesDeAnalise();
        return ResponseEntity.ok().body(lista);
    }

    /**
     * Método HTTP que retorna uma Solicitação de Análise a partir de seu id
     * @param idSA Id da Solicitação de Análise a ser retornada
     * @return Entidade de resposta com a solicitação de análise retornada
     */
    @GetMapping("/{idSA}")
    public ResponseEntity<SolicitacaoDeAnalise> retornarSolicitacaoDeAnalisePorId(@PathVariable String idSA){
        SolicitacaoDeAnalise solicitacaoDeAnalise = solicitacaoDeAnaliseService.procurarSolicitacaoDeAnalisePeloId(idSA);
        return ResponseEntity.status(HttpStatus.OK).body(solicitacaoDeAnalise);
    }

    /**
     * Método HTTP que atualiza dados da Solicitação de Análise
     * @param idSA id da Solicitação que será atualizada
     * @param solicitacaoDeAnaliseDto dados que serão atualizados na Solicitação de Análise
     * @return Entidade de resposta com a Solicitação de Análise atualizada
     */
    @PutMapping("/{idSA}")
    public ResponseEntity<SolicitacaoDeAnalise> atualizarSolicitacaoDeAnalise(@PathVariable String idSA, @RequestBody SolicitacaoDeAnaliseDto solicitacaoDeAnaliseDto){
        SolicitacaoDeAnalise solicitacaoDeAnalise = solicitacaoDeAnaliseService.procurarSolicitacaoDeAnalisePeloId(idSA);
        solicitacaoDeAnalise = solicitacaoDeAnaliseService.atualizarDadosSolicitacaoDeAnalise(solicitacaoDeAnalise, solicitacaoDeAnaliseDto);
        return ResponseEntity.status(HttpStatus.OK).body(solicitacaoDeAnaliseService.salvarSolicitacaoDeAnalise(solicitacaoDeAnalise));
    }

    /**
     * Método HTTP que retorna uma lista de Solicitações de Análise de um solicitante
     * @param solicitante Solicitante das Solicitações de Análise
     * @return Entidade de resposta com lista de Solicitações de Análise
     */
    @GetMapping("/solicitante/{solicitante}")
    public ResponseEntity<List<SolicitacaoDeAnalise>> solicitacaoDeAnalisePorSolicitante(@PathVariable String solicitante){
        Solicitante solicitanteModel = solicitanteService.procurarSolicitantePeloCnpj(solicitante);
        return ResponseEntity.status(HttpStatus.OK).body(solicitacaoDeAnaliseService.procurarSolicitacaoDeAnalisePeloSolicitante(solicitanteModel));
    }
}
