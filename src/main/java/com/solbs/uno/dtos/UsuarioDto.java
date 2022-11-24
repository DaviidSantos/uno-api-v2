package com.solbs.uno.dtos;

import com.solbs.uno.entities.enums.Cargos;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UsuarioDto {
    @NotBlank
    private String nome;

    @NotBlank
    private Long cargo;

    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6)
    private String senha;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCargo() {
        return cargo;
    }

    public void setCargo(Long cargo) {
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
}
