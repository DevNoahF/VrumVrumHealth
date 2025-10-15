package com.devnoahf.vrumvrumhealth.Model;

import com.devnoahf.vrumvrumhealth.Enum.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "adm")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
// Não descomentar a implementação(necessario terminar as configurações de segurança antes)
public class Adm /*implements UserDetails*/ {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String matricula;

    @Column(nullable = false, unique = true)
    private String email;


    private String senhaHash;


    @Column(nullable = false, updatable = false, name = "created_at")
    private LocalDateTime  createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now(); // opcional
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    //TODOS OS METODOS ESTÃO COMENTADOS POIS AINDA NÃO FORAM FINALIZADAS AS CONFIGURAÇÕES DE SEGURANÇA
    //método para definir as autoridades do usuário com base no papel
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        if (this.role == Role.ADMIN) return List.of(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority(("USER")));
//        else return List.of(new SimpleGrantedAuthority("USER"));
//
//    }

//    @Override
//    public String getPassword() {
//        return "";
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
}
