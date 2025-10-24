package com.devnoahf.vrumvrumhealth.Enum;

public enum FrequenciaEnum {
    SEMANAL("semanal"),
    QUINZENAL("quinzenal"),
    MENSAL("mensal"),
    DIARIO("diario");

    private String frequencia;

    FrequenciaEnum(String frequencia) {
        this.frequencia = frequencia;
    }

    public String getFrequencia() {
        return frequencia;
    }
}
