package com.solbs.uno.services;

import com.solbs.uno.dtos.SolicitacaoDeAnaliseDto;
import com.solbs.uno.entities.SolicitacaoDeAnalise;
import com.solbs.uno.entities.Solicitante;
import com.solbs.uno.repositories.SolicitacaoDeAnaliseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SolicitacaoDeAnaliseService {
    @Autowired
    private SolicitacaoDeAnaliseRepository solicitacaoDeAnaliseRepository;

    /**
     * Método que salva uma solicitação de análise na base de dados
     * @param solicitacaoDeAnalise Solicitação a ser salva na base de dados
     * @return Solicitação salva
     */
    @Transactional
    public SolicitacaoDeAnalise salvarSolicitacaoDeAnalise(SolicitacaoDeAnalise solicitacaoDeAnalise){
        return solicitacaoDeAnaliseRepository.save(solicitacaoDeAnalise);
    }

    /**
     * Método que retorna uma lista com todas as Solicitações de Análise
     * @return Lista de Solicitações de Análise
     */
    public List<SolicitacaoDeAnalise> procurarTodasSolicitacoesDeAnalise(){
        return solicitacaoDeAnaliseRepository.findAll();
    }

    /**
     * Método que retorna uma Solicitação de Análise a partir de seu ID
     * @param idSA ID da Solicitação de Análise
     * @return Solicitação de Análise
     */
    public SolicitacaoDeAnalise procurarSolicitacaoDeAnalisePeloId(String idSA) {
        return solicitacaoDeAnaliseRepository.findById(idSA).get();
    }

    /**
     * Método que retorna uma lista de Solicitações de Análise de um solicitante
     * @param solicitante Solicitante das Solicitações de Análise
     * @return Lista de Solicitações de Análise
     */
    public List<SolicitacaoDeAnalise> procurarSolicitacaoDeAnalisePeloSolicitante(Solicitante solicitante){
        return solicitacaoDeAnaliseRepository.findSolicitacaoDeAnaliseBySolicitante(solicitante);
    }

    /**
     * Método que atualiza os dados de uma Solicitação de Análise
     * @param solicitacaoDeAnalise Solicitação de Análise que terá os dados atualizados
     * @param solicitacaoDeAnaliseDto Dados que serão atualizados na Solicitação de Análise
     * @return Solicitação de Análise Atualizada
     */
    public SolicitacaoDeAnalise atualizarDadosSolicitacaoDeAnalise(SolicitacaoDeAnalise solicitacaoDeAnalise, SolicitacaoDeAnaliseDto solicitacaoDeAnaliseDto) {
        if(solicitacaoDeAnaliseDto.getTipoDeAnalise() != null){
            solicitacaoDeAnalise.setTipoDeAnalise(solicitacaoDeAnaliseDto.getTipoDeAnalise());
        }

        if(solicitacaoDeAnaliseDto.getConsideracoesGerais() != null){
            solicitacaoDeAnalise.setConsideracoesGerais(solicitacaoDeAnaliseDto.getConsideracoesGerais());
        }

        if (solicitacaoDeAnaliseDto.getInformacoesAdicionais() != null){
            solicitacaoDeAnalise.setInformacoesAdicionais(solicitacaoDeAnaliseDto.getInformacoesAdicionais());
        }

        return solicitacaoDeAnalise;
    }
}
