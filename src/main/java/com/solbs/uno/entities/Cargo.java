package com.solbs.uno.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.solbs.uno.entities.enums.Cargos;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_cargo")
public class Cargo implements GrantedAuthority, Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCargo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private Cargos nome;

    @JsonIgnore
    @OneToMany(mappedBy = "cargo")
    private Set<Usuario>  usuarios = new HashSet<>();

    public Cargo() {
    }

    public Cargo(Long idCargo, Cargos nome) {
        this.idCargo = idCargo;
        this.nome = nome;
    }

    @Override
    public String getAuthority() {
        return this.nome.toString();
    }

    public Long getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Long idCargo) {
        this.idCargo = idCargo;
    }

    public Cargos getNome() {
        return nome;
    }

    public void setNome(Cargos nome) {
        this.nome = nome;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }
}
