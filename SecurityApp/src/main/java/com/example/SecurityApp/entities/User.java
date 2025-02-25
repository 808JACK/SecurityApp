package com.example.SecurityApp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true)
    private String email;
    private String password;

    public User() {
    }

    // Parameterized Constructor for Custom Initialization
    public User(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Example: Add authorities here if needed (e.g., USER, ADMIN roles)
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Customize if you want to manage expiration
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Customize if you want to manage locked accounts
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Customize for credential expiry
    }

    @Override
    public boolean isEnabled() {
        return true; // Customize if users can be disabled
    }
}
