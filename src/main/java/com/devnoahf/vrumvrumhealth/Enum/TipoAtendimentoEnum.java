package com.devnoahf.vrumvrumhealth.Enum;

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
