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

    public static final class UserBuilder {
        private Long id;
        private String name;
        private String email;
        private String password;

        private UserBuilder() {
        }

        public static UserBuilder anUser() {
            return new UserBuilder();
        }

        public UserBuilder builderId(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder builderName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder builderEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder builderPassword(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            User user = new User();
            user.setId(id);
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            return user;
        }
    }
}
