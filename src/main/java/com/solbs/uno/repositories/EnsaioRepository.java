package com.solbs.uno.repositories;

import com.solbs.uno.entities.Ensaio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnsaioRepository extends JpaRepository<Ensaio, String> {
    @Query(value = "SELECT * FROM TB_ENSAIO WHERE ID_AMOSTRA = ?1", nativeQuery = true)
    List<Ensaio> findEnsaioByAmostra(Long id);
}
