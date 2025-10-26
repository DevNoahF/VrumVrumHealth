package com.devnoahf.vrumvrumhealth.Enum;


import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

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
