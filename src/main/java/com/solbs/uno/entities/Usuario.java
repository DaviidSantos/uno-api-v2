package com.solbs.uno.entities;

import com.solbs.uno.entities.enums.Cargos;
import com.solbs.uno.entities.customid.IdPrefixado;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_usuario")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "us_seq")
    @GenericGenerator(name = "us_seq", strategy = "com.solbs.uno.entities.customid.IdPrefixado",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = IdPrefixado.INCREMENT_PARAM, value = "100"),
                    @org.hibernate.annotations.Parameter(name = IdPrefixado.VALUE_PREFIX_PARAMETER, value = "US_"),
                    @org.hibernate.annotations.Parameter(name = IdPrefixado.NUMBER_FORMAT_PARAMETER, value = "%05d")
            })
    private Long idUsuario;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Cargos cargo;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    public Usuario() {
    }

    public Usuario(Long idUsuario, String nome, Cargos cargo, String email, String senha) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.cargo = cargo;
        this.email = email;
        this.senha = senha;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Cargos getCargo() {
        return cargo;
    }

    public void setCargo(Cargos cargo) {
        this.cargo = cargo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;

        return Objects.equals(idUsuario, usuario.idUsuario);
    }

    @Override
    public int hashCode() {
        return idUsuario != null ? idUsuario.hashCode() : 0;
    }

}
