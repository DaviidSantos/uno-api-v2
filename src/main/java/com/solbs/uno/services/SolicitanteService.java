package com.solbs.uno.services;

import com.solbs.uno.dtos.SolicitanteDto;
import com.solbs.uno.entities.Solicitante;
import com.solbs.uno.repositories.SolicitanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SolicitanteService {
    @Autowired
    SolicitanteRepository solicitanteRepository;

    /**
     * Método que salva um solicitante na base de dados
     * @param solicitante Solicitante que será salvo
     * @return Solicitante salvo
     */
    @Transactional
    public Solicitante salvarSolicitante(Solicitante solicitante){
        return solicitanteRepository.save(solicitante);
    }

    /**
     * Método que retorna uma lista com todos os solicitantes
     * @return Lista de Solicitantes
     */
    public List<Solicitante> procurarTodosSolicitantes(){
        return solicitanteRepository.findAll();
    }

    /**
     * Método que retorna um solicitante a partir de seu CNPJ
     * @param cnpj CNPJ do solicitante
     * @return Solicitantee
     */
    public Solicitante procurarSolicitantePeloCnpj(String cnpj){
        return solicitanteRepository.findById(cnpj).get();
    }

    /**
     * Método que atualiza os dados de um solicitante
     * @param solicitante Solicitante que terá os dados atualizados
     * @param dados Dados do solicitante que serão atualizados
     * @return Solicitante atualizado
     */
    public Solicitante atualizarDadosSolicitante(Solicitante solicitante, SolicitanteDto dados) {
        if (dados.getNomeFantasia() != null) {
            solicitante.setNomeFantasia(dados.getNomeFantasia());
        }

        if (dados.getCep() != null) {
            solicitante.setCep(dados.getCep());
        }

        if (dados.getEndereco() != null) {
            solicitante.setEndereco(dados.getEndereco());
        }

        if(dados.getCidade() != null){
            solicitante.setCidade(dados.getCidade());
        }

        if (dados.getEstado() != null) {
            solicitante.setEstado(dados.getEstado());
        }

        if (dados.getEmailComercial() != null) {
            solicitante.setEmailComercial(dados.getEmailComercial());
        }

        if (dados.getTelefoneComercial() != null) {
            solicitante.setTelefoneComercial(dados.getTelefoneComercial());
        }
        return solicitante;
    }

    public boolean existsByCnpj(String cnpj) {
        return solicitanteRepository.existsByCnpj(cnpj);
    }
}
