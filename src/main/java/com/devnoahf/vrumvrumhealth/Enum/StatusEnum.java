package com.devnoahf.vrumvrumhealth.Enum;

public enum StatusEnum { // status do comprovante
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
