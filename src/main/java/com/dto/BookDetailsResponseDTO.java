package com.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class BookDetailsResponseDTO {
    private Integer id;
    private String isbn;
    private Date publicationDate;
    private int numberOfPages;
    private String language;
    private String bookTitle;
}