package com.solbs.uno.dtos;

public class QuantidadeDeAmostraPorStatusDto {
    int amostraFinalizada;
    int amostraEmAnalise;
    int amostraAguardandoAnalise;

    public QuantidadeDeAmostraPorStatusDto() {
    }

    public int getAmostraFinalizada() {
        return amostraFinalizada;
    }

    public void setAmostraFinalizada(int amostraFinalizada) {
        this.amostraFinalizada = amostraFinalizada;
    }

    public int getAmostraEmAnalise() {
        return amostraEmAnalise;
    }

    public void setAmostraEmAnalise(int amostraEmAnalise) {
        this.amostraEmAnalise = amostraEmAnalise;
    }

    public int getAmostraAguardandoAnalise() {
        return amostraAguardandoAnalise;
    }

    public void setAmostraAguardandoAnalise(int amostraAguardandoAnalise) {
        this.amostraAguardandoAnalise = amostraAguardandoAnalise;
    }
}
