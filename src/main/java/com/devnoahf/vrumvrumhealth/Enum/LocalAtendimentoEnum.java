package com.devnoahf.vrumvrumhealth.Enum;

public enum LocalAtendimentoEnum {
    UPA1("upa1"),
    UPA2("upa2"),
    HOSPITAL1("hospital1"),
    HOSPITAL2("hospital2");

    private String localAtendimento;

    LocalAtendimentoEnum(String localAtendimento) {
        this.localAtendimento = localAtendimento;
    }

    public String getLocalAtendimento() {
        return localAtendimento;
    }
}


