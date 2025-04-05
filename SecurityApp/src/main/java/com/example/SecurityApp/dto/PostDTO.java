package com.example.SecurityApp.dto;

import jakarta.persistence.ManyToOne;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDTO {
    private Long id;
    private String title;
    private String description;

    @ManyToOne
    private UserDTO author;
}
