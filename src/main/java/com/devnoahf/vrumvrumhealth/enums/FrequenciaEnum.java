package com.devnoahf.vrumvrumhealth.enums;

public enum FrequenciaEnum {
    SEMANAL("semanal"),
    QUINZENAL("quinzenal"),
    MENSAL("mensal"),
    DIARIO("diario"),
    NENHUMA("nenhuma");


    public final String frequencia;

    FrequenciaEnum(String frequencia){
        this.frequencia = frequencia;
   }

    public String getFrequencia() {
        return frequencia;
    }
}
