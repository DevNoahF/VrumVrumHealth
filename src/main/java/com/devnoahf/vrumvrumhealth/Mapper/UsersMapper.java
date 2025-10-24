package com.devnoahf.vrumvrumhealth.Mapper;

import com.devnoahf.vrumvrumhealth.DTO.UsersDTO;
import com.devnoahf.vrumvrumhealth.Entity.Users;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Builder
@Component
@RequiredArgsConstructor
public class UsersMapper {

    private final BCryptPasswordEncoder passwordEncoder;


    public UsersMapper(){
        this.passwordEncoder = new BCryptPasswordEncoder();
        }
        


    public static Users toEntity(UsersDTO dto){
        return Users.builder()
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .build();
    }

    public static UsersDTO toDTO(Users users) {
        String encoder = new BCryptPasswordEncoder().encode(users.getSenha());

        return UsersDTO.builder()
                .email(users.getEmail())
                .senha(encoder)
                .build();
    }
}
