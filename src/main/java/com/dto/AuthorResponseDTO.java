package com.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
public class AuthorResponseDTO {
    private Integer id;
    private String name;
    private List<String> bookTitles;
}
