package com.devnoahf.vrumvrumhealth.Enum;


public enum RoleEnum {
    ADMIN("admin"),
    PACIENTE("paciente"),
    MOTORISTA("motorista");

    private String role;

    RoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
