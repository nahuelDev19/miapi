package com.api.rest.miapi.entitys;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Data
@Table(name = "users")
@Entity
public class UserApi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotBlank
    private String username;
    @NotBlank @Size(min = 4)
    private String password;

    @ManyToMany
    @JoinTable(name = "user_x_roles",
    joinColumns = @JoinColumn(name= "user_id"),
    inverseJoinColumns = @JoinColumn(name= "role_id"),
    uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})} )
    private List<Role> roles;

    private boolean enabled;
    @Transient
    private boolean admin;


    public UserApi(@NotBlank String username,
     @NotBlank @Size(min = 4) String password,
     List<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        
    }


    

}
