package com.devnoahf.vrumvrumhealth.Enum;


public enum RoleEnum {
    ADMIN("admin"),
    USER("user"),
    MOTORISTA("motorista"),
    PACIENTE("paciente");

    private String role;

    RoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
