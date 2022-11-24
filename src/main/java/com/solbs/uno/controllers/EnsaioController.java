package com.solbs.uno.controllers;

import com.solbs.uno.dtos.EnsaioDto;
import com.solbs.uno.entities.Amostra;
import com.solbs.uno.entities.Ensaio;
import com.solbs.uno.services.AmostraService;
import com.solbs.uno.services.EnsaioService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ensaio")
@CrossOrigin("*")
public class EnsaioController {
    @Autowired
    private EnsaioService ensaioService;

    @Autowired
    private AmostraService amostraService;

    /**
     * Método HTTP que cadastra um ensaio na base de dados
     * @param ensaioDto Dados do ensaio a ser cadastrado
     * @return Entidadde de resposta com o ensaio cadastrado
     */
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ANALISTA')")
    @PostMapping
    public ResponseEntity<Ensaio> cadastrarEnsaio(@RequestBody EnsaioDto ensaioDto){
        Ensaio ensaio = new Ensaio();
        Amostra amostra = amostraService.procurarAmostraPeloId(ensaioDto.getAmostra());
        BeanUtils.copyProperties(ensaioDto, ensaio);
        ensaio.setAmostra(amostra);
        ensaio = ensaioService.salvarEnsaio(ensaio);
        return ResponseEntity.status(HttpStatus.CREATED).body(ensaio);
    }

    /**
     * Método HTTP que retorna uma lista de todos os ensaios cadastrados na base de dados
     * @return Entidade de resposta com lista de ensaios
     */
    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<List<Ensaio>> retornarTodosEnsaios(){
        List<Ensaio> ensaios = ensaioService.procurarTodosOsEnsaios();
        return ResponseEntity.ok().body(ensaios);
    }

    /**
     * Método HTTP que retorna um ensaio a partir de seu id
     * @param idEnsaio Id do ensaio
     * @return Entidade de resposta com o ensaio
     */
    @PreAuthorize("permitAll()")
    @GetMapping("/{idEnsaio}")
    public ResponseEntity<Ensaio> retornarEnsaioPorId(@PathVariable String idEnsaio){
        Ensaio ensaio = ensaioService.procurarEnsaioPeloId(idEnsaio);
        return ResponseEntity.status(HttpStatus.OK).body(ensaio);
    }

    /**
     * Método HTTP que retorna uma lista de ensaios a partir de uma amostra
     * @param idAmostra Id da Amostra
     * @return Lista de Ensaio
     */
    @GetMapping("/por-amostra/{idAmostra}")
    public ResponseEntity<List<Ensaio>> retornarEnsaioPorAmostra(@PathVariable Long idAmostra){
        return ResponseEntity.status(HttpStatus.OK).body(ensaioService.procurarEnsaiosPorAmostra(idAmostra));
    }

    /**
     * Método HTTP que cadastra o resultado do ensaio
     * @param resultado Resultado do ensaio
     * @return Entidade de resposta com o ensaio atualizado
     */
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ANALISTA')")
    @PutMapping("/{idEnsaio}")
    public ResponseEntity<Ensaio> resultadoDoEnsaio(@PathVariable String idEnsaio, @RequestBody EnsaioDto resultado){
        Ensaio ensaio = ensaioService.procurarEnsaioPeloId(idEnsaio);
        ensaio.setResultadoDoEnsaio(resultado.getResultadoDoEnsaio());
        ensaio = ensaioService.salvarEnsaio(ensaio);
        return ResponseEntity.status(HttpStatus.OK).body(ensaio);
    }
}
