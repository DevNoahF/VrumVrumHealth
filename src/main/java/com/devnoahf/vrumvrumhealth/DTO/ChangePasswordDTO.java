package com.devnoahf.vrumvrumhealth.DTO;

import lombok.Data;

@Data
public class ChangePasswordDTO {
    private String currentPassword;
    private String newPassword;
}
