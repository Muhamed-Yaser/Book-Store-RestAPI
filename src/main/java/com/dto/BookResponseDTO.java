package com.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookResponseDTO {

    private Integer id;
    private String title;
    private Double price;
    private String categoryName;
    private List<String> authorNames;

}
