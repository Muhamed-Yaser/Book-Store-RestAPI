package com.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookRequestDTO {

    @NotBlank(message = "book title can not be empty")
    @Size(min = 2, max = 150, message = "Title must be between 2 and 150 characters")
    private String title;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    @NotNull(message = "Category id is required")
    private Integer CategoryId;

    @NotEmpty(message = "Book must have at least one author")
    private List<Integer> authorIds;
}
