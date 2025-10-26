package com.devnoahf.vrumvrumhealth.Enum;

public enum StatusEnum {
    PENDENTE("pendente"),
    APROVADO("aprovado"),
    NEGADO("negado");

    private String status;

    StatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
