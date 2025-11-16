package com.devnoahf.vrumvrumhealth.model;

import com.devnoahf.vrumvrumhealth.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "adm")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
// Não descomentar a implementação(necessario terminar as configurações de segurança antes)
public class Adm  /*implements UserDetails*/ {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String matricula;

    @Column(nullable = false, unique = true)
    private String email;


    private String senha;


    @Column(nullable = false, updatable = false, name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;



    public Adm(RoleEnum roleEnum) {
        this.setRoleEnum(roleEnum.ADMIN);
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now(); // opcional
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;



    //TODOS OS METODOS ESTÃO COMENTADOS POIS AINDA NÃO FORAM FINALIZADAS AS CONFIGURAÇÕES DE SEGURANÇA
    //método para definir as autoridades do usuário com base no papel
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        if (this.role == Role.ADMIN) return List.of(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority(("USER")));
//        else return List.of(new SimpleGrantedAuthority("USER"));
//
//    }
}

