package com.devnoahf.vrumvrumhealth.Enum;

public enum LocalAtendimentoEnum {
    UPA1("upa 1"),
    UPA2("upa 2"),
    HOSPITAL1("hospital 1"),
    HOSPITAL2("hospital 2");

    private String localAtendimento;


    LocalAtendimentoEnum(String localAtendimento) {
        this.localAtendimento = localAtendimento;
    }

    public String getLocalAtendimento() {
        return localAtendimento;
    }

}


