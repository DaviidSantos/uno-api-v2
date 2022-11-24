package com.solbs.uno.services;

import com.solbs.uno.dtos.QuantidadeDeAmostraPorStatusDto;
import com.solbs.uno.entities.Amostra;
import com.solbs.uno.entities.SolicitacaoDeAnalise;
import com.solbs.uno.repositories.AmostraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AmostraService {
    @Autowired
    private AmostraRepository amostraRepository;

    /**
     * Método que salva uma amostra na base de dados
     * @param amostra Amostra a ser salva
     * @return Amostra salva
     */
    @Transactional
    public Amostra salvarAmostra(Amostra amostra){
        return amostraRepository.save(amostra);
    }

    /**
     * Método que retorna todas as amostras cadastradas
     * @return Lista de amostras
     */
    public List<Amostra> procurarTodasAsAmostras(){
        return amostraRepository.findAll();
    }

    /**
     * Método que retorna uma amostra a partir do id
     * @param idAmostra Id da amostra
     * @return Amostra
     */
    public Amostra procurarAmostraPeloId(String idAmostra) {
        return amostraRepository.findById(idAmostra).get();
    }

    /**
     * Método que retorna todas as amostras com status: análise finalizada
     * @return Lista de amostras com analise finalizada
     */
    public List<Amostra> procurarAmostrasFinalizadas() {
        return amostraRepository.findAmostrasAnaliseFinalizada();
    }

    /**
     * Método que retorna todas as amostras com status: em análise
     * @return Lista de amostras em análise
     */
    public List<Amostra> procurarAmostrasEmAnalise(){
        return amostraRepository.findAmostraEmAnalise();
    }

    /**
     * Método que retorna todas as amostras com status: aguardando análise
     * @return Lista de amostras aguardando análise
     */
    public List<Amostra> procurarAmostrasAguardandoAnalise() {
        return amostraRepository.findAmostraAguardandoAnalise();
    }

    /**
     * Método que retorna todas as amostras com status: em falta
     * @return Lista de amostras em falta
     */
    public List<Amostra> procurarAmostrasEmFalta() {
        return amostraRepository.findAmostraEmFalta();
    }

    /**
     * Método que retorna todas as amostras de uma solicitação de análise
     * @param solicitacaoDeAnalise Solicitação de Análise
     * @return Lista de Amostras
     */
    public List<Amostra> procurarAmostraPorSolicitacaoDeAnalise(SolicitacaoDeAnalise solicitacaoDeAnalise){
        return amostraRepository.findAmostraBySolicitacaoDeAnalise(solicitacaoDeAnalise);
    }

    /**
     * Método que retorna a quatidade de amostras em cada Status
     * @return QuantidadeStatusAmostra
     */
    public QuantidadeDeAmostraPorStatusDto retornarQuantidadeAmostraPorStatus(){
        QuantidadeDeAmostraPorStatusDto quantidadeStatusAmostra = new QuantidadeDeAmostraPorStatusDto();
        quantidadeStatusAmostra.setAmostraFinalizada(amostraRepository.quantidadeFinalizada());
        quantidadeStatusAmostra.setAmostraEmAnalise(amostraRepository.quantidadeEmAnalise());
        quantidadeStatusAmostra.setAmostraAguardandoAnalise(amostraRepository.quantidadeAguardandoAnalise());
        return quantidadeStatusAmostra;
    }
}
