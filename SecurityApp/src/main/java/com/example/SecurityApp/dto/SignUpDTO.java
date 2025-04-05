package com.example.SecurityApp.dto;

import com.example.SecurityApp.entities.enums.Permissions;
import com.example.SecurityApp.entities.enums.Roles;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDTO {

    private String email;
    private String password;
    private String name;
    private Set<Roles> roles;
    private Set<Permissions> permissions;
}
