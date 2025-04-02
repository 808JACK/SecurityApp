package com.example.SecurityApp.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor  // Default constructor required by JPA
@AllArgsConstructor
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull  // Enforces non-null during object creation
    private String refreshToken;

    @CreationTimestamp
    private LocalDateTime lastUsedAt;

    @ManyToOne
    @NonNull  // Ensures user is always set
    private User user;

    // Lombok's Builder will use this constructor
    @Builder
    public Session(@NonNull String refreshToken, @NonNull User user) {
        this.refreshToken = refreshToken;
        this.user = user;
        this.lastUsedAt = LocalDateTime.now(); // Explicitly set default value
    }
}
