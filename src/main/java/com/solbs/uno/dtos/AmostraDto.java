package com.solbs.uno.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class AmostraDto {

    @NotBlank
    private String solicitacaoDeAnalise;
    private int statusAmostra;

    @NotBlank
    private String nomeAmostra;

    @NotBlank
    private String tipo;

    @NotBlank
    @Size(min = 9, max = 9)
    private String notaFiscal;

    @NotBlank
    private LocalDate validade;

    public String getSolicitacaoDeAnalise() {
        return solicitacaoDeAnalise;
    }

    public void setSolicitacaoDeAnalise(String solicitacaoDeAnalise) {
        this.solicitacaoDeAnalise = solicitacaoDeAnalise;
    }

    public int getStatusAmostra() {
        return statusAmostra;
    }

    public void setStatusAmostra(int statusAmostra) {
        this.statusAmostra = statusAmostra;
    }

    public String getNomeAmostra() {
        return nomeAmostra;
    }

    public void setNomeAmostra(String nomeAmostra) {
        this.nomeAmostra = nomeAmostra;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }
}
