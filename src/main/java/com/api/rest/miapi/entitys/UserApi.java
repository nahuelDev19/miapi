package com.api.rest.miapi.entitys;


import java.util.ArrayList;
import java.util.List;

import com.api.rest.miapi.validationx.ExistByUserName;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

 @AllArgsConstructor @Data
@Table(name = "users")
@Entity
public class UserApi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotBlank @ExistByUserName
    private String username;
    @NotBlank @Size(min = 4)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ManyToMany
    @JoinTable(name = "user_x_roles",
    joinColumns = @JoinColumn(name= "user_id"),
    inverseJoinColumns = @JoinColumn(name= "role_id"),
    uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})} )
    private List<Role> roles;

    private boolean enabled;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean admin;

    @PrePersist
    private void prePersiste(){
        enabled=true;
    }

    public UserApi(@NotBlank String username,
     @NotBlank @Size(min = 4) String password,
     List<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        
    }

    public UserApi() {
        this.roles= new ArrayList<>();
    }


    

}
