package com.solbs.uno.controllers;

import com.solbs.uno.dtos.AmostraDto;
import com.solbs.uno.dtos.QuantidadeDeAmostraPorStatusDto;
import com.solbs.uno.entities.Amostra;
import com.solbs.uno.entities.SolicitacaoDeAnalise;
import com.solbs.uno.entities.enums.StatusAmostra;
import com.solbs.uno.services.AmostraService;
import com.solbs.uno.services.SolicitacaoDeAnaliseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/amostra")
@CrossOrigin("*")
public class AmostraController {
    @Autowired
    private AmostraService amostraService;

    @Autowired
    private SolicitacaoDeAnaliseService solicitacaoDeAnaliseService;

    /**
     * Método HTTP que cadastra uma amostra na base de dados
     * @param amostraDto dados da amostra a ser cadastrada
     * @return Entidade de resposta com a amostra cadastrada
     */
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EXPEDIÇÃO')")
    @PostMapping
    public ResponseEntity<Amostra> cadastrarAmostra(@RequestBody AmostraDto amostraDto){
        Amostra amostra = new Amostra();
        SolicitacaoDeAnalise solicitacaoDeAnalise = solicitacaoDeAnaliseService.procurarSolicitacaoDeAnalisePeloId(amostraDto.getSolicitacaoDeAnalise());
        BeanUtils.copyProperties(amostraDto, amostra);
        StatusAmostra statusAmostra = StatusAmostra.Aguardando_Análise;
        amostra.setStatusAmostra(statusAmostra);
        amostra.setSolicitacaoDeAnalise(solicitacaoDeAnalise);
        amostra.setDataDeEntrada(Instant.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(amostraService.salvarAmostra(amostra));
    }


    /**
     * Método HTTP que retorna uma lista com todas as amostras na base de dados
     * @return Entidade de resposta com a lista com amostras
     */
    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<List<Amostra>> retornarTodasAmostras(){
        List<Amostra> amostras = amostraService.procurarTodasAsAmostras();
        return ResponseEntity.ok().body(amostras);
    }


    /**
     * Método HTTP que retorna uma amostra a partir do id
     * @param idAmostra id da amostra a ser retornada
     * @return Entidade de resposta com a amostra
     */
    @PreAuthorize("permitAll()")
    @GetMapping("/{idAmostra}")
    public ResponseEntity<Amostra> retornarAmostraPorId(@PathVariable String idAmostra){
        Amostra amostra = amostraService.procurarAmostraPeloId(idAmostra);
        return ResponseEntity.status(HttpStatus.OK).body(amostra);
    }

    /**
     * Método HTTP que atualiza o Status da amostra
     * @param idAmostra id da amostra que terá o status atualizado
     * @param status código int do novo status
     * @return Entidade de resposta com a amostra atualizada
     */
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ANALISTA')")
    @PutMapping("/{idAmostra}")
    public ResponseEntity<Amostra> atualizarStatusDaAmostra(@PathVariable String idAmostra, @RequestBody int status){
        Amostra amostra = amostraService.procurarAmostraPeloId(idAmostra);
        StatusAmostra statusAmostra = StatusAmostra.valor(status);
        amostra.setStatusAmostra(statusAmostra);
        return ResponseEntity.status(HttpStatus.OK).body(amostraService.salvarAmostra(amostra));
    }

    /**
     * Método HTTP que retorna uma lista de amostras com analises concluídas
     * @return entidade de resposta com lista de amostras
     */
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ANALISTA')")
    @GetMapping("/concluido")
    public ResponseEntity<List<Amostra>> procurarAmostrasFinalizadas(){
        List<Amostra> amostrasConcluidas = amostraService.procurarAmostrasFinalizadas();
        return ResponseEntity.status(HttpStatus.OK).body(amostrasConcluidas);
    }

    /**
     * Método HTTP que retorna uma lista com amostras em análise
     * @return entidade de resposta com lista de amostras
     */
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ANALISTA')")
    @GetMapping("/em-analise")
    public ResponseEntity<List<Amostra>> procurarAmostrasEmAnalise(){
        List<Amostra> amostrasEmAnalise = amostraService.procurarAmostrasEmAnalise();
        return ResponseEntity.status(HttpStatus.OK).body(amostrasEmAnalise);
    }

    /**
     * Método HTTP que retorna uma lista com amostras aguardando análise
     * @return Entidade de resposta com lista de amostras
     */
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ANALISTA')")
    @GetMapping("/aguardando-analise")
    public ResponseEntity<List<Amostra>> procurarAmostrasAguardandoAnalise(){
        List<Amostra> amostrasAguardandoAnalise = amostraService.procurarAmostrasAguardandoAnalise();
        return ResponseEntity.status(HttpStatus.OK).body(amostrasAguardandoAnalise);
    }

    /**
     * Método HTTP que retorna uma lista de amostras em falta
     * @return Entidade de resposta com lista de amostras
     */
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ANALISTA')")
    @GetMapping("/amostra-em-falta")
    public ResponseEntity<List<Amostra>> procurarAmostrasEmFalta(){
        List<Amostra> amostrasEmFalta = amostraService.procurarAmostrasEmFalta();
        return ResponseEntity.status(HttpStatus.OK).body(amostrasEmFalta);
    }

    /**
     * Método HTTP que retorna a quantidade de amostra em cada status
     * @return Entidade de resposta com a quantidade de amostra em cada status
     */
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ANALISTA')")
    @GetMapping("/quantidade-status")
    public ResponseEntity<QuantidadeDeAmostraPorStatusDto> quantidadeAmostrasPorStatus(){
        return ResponseEntity.status(HttpStatus.OK).body(amostraService.retornarQuantidadeAmostraPorStatus());
    }

    /**
     * Método HTTP que retorna uma lista de amostras a partir de sua solicitação de análise
     * @param idSA Id da solicitação de análise
     * @return Lista de Amostra
     */
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ANALISTA')")
    @GetMapping("/solicitacao-de-analise/{idSA}")
    public ResponseEntity<List<Amostra>> procurarAmostraPorSolicitacaoDeAnalise(@PathVariable String idSA){
        SolicitacaoDeAnalise solicitacaoDeAnalise = solicitacaoDeAnaliseService.procurarSolicitacaoDeAnalisePeloId(idSA);
        return ResponseEntity.status(HttpStatus.OK).body(amostraService.procurarAmostraPorSolicitacaoDeAnalise(solicitacaoDeAnalise));
    }
}
