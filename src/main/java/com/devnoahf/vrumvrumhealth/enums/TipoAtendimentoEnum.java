package com.devnoahf.vrumvrumhealth.enums;

public enum TipoAtendimentoEnum {
    EXAME("exame"),
    CONSULTA("consulta"),
    TRATAMENTO_CONTINUO("tratamento continuo");

    private String tipoAtendimento;

    TipoAtendimentoEnum(String tipoAtendimento) {
        this.tipoAtendimento = tipoAtendimento;
    }

    public String getTipoAtendimento() {
        return tipoAtendimento;
    }
}
