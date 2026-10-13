package com.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorRequestDTO {

    @NotBlank(message = "Author name cannot be blank")
    @Size(min = 2, max = 100, message = "Author name must be between 2 and 100 characters")
    private String name;
}