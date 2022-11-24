package com.solbs.uno.repositories;

import com.solbs.uno.entities.Solicitante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitanteRepository extends JpaRepository<Solicitante, String> {
    boolean existsByCnpj(String cnpj);
}
