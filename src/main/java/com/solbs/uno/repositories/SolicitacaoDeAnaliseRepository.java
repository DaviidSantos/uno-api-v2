package com.solbs.uno.repositories;

import com.solbs.uno.entities.SolicitacaoDeAnalise;
import com.solbs.uno.entities.Solicitante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitacaoDeAnaliseRepository extends JpaRepository<SolicitacaoDeAnalise, String> {
    List<SolicitacaoDeAnalise> findSolicitacaoDeAnaliseBySolicitante(Solicitante solicitante);
}
