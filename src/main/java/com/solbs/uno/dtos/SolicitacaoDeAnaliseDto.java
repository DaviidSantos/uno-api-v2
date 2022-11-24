package com.solbs.uno.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SolicitacaoDeAnaliseDto {
    @NotBlank
    @Size(min = 14, max = 14)
    private String cnpj;

    @NotBlank
    private String tipoDeAnalise;
    private String informacoesAdicionais;
    private String consideracoesGerais;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTipoDeAnalise() {
        return tipoDeAnalise;
    }

    public void setTipoDeAnalise(String tipoDeAnalise) {
        this.tipoDeAnalise = tipoDeAnalise;
    }

    public String getInformacoesAdicionais() {
        return informacoesAdicionais;
    }

    public void setInformacoesAdicionais(String informacoesAdicionais) {
        this.informacoesAdicionais = informacoesAdicionais;
    }

    public String getConsideracoesGerais() {
        return consideracoesGerais;
    }

    public void setConsideracoesGerais(String consideracoesGerais) {
        this.consideracoesGerais = consideracoesGerais;
    }
}
