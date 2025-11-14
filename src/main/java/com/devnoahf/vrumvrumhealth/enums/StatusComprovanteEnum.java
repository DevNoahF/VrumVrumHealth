package com.devnoahf.vrumvrumhealth.enums;

public enum StatusComprovanteEnum {
    PENDENTE("pendente"),
    APROVADO("aprovado"),
    NEGADO("negado");


    private String status;
    StatusComprovanteEnum(String status){
        this.status = status;
    }

    public String getStatusComprovante(){
        return status;
    }
}
