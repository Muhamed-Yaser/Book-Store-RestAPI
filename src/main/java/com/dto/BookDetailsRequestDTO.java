package com.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class BookDetailsRequestDTO {

    @NotBlank(message = "ISBN is required")
    private String isbn;

    private Date publicationDate;

    @Min(value = 1, message = "Number of pages must be greater than 0")
    private int numberOfPages;

    private String language;
}