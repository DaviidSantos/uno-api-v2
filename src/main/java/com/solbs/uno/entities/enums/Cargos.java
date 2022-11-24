package com.solbs.uno.entities.enums;

public enum Cargos {
    ADMIN(1),
    ANÁLISTA(2),
    VENDEDOR(3),
    EXPEDIÇÃO(4);

    private int code;

    Cargos(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Cargos valor(int code){
        for(Cargos value: Cargos.values()){
            if (value.getCode() == code){
                return value;
            }
        }
        throw new IllegalArgumentException("Código do cargo inválido");
    }
}
