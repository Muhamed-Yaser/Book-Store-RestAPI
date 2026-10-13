package com.controller;

import com.dto.AuthorRequestDTO;
import com.dto.AuthorResponseDTO;
import com.model.Author;
import com.model.Book;
import com.repository.AuthorRepository;
import com.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    private AuthorResponseDTO mapToDTO(Author author) {
        AuthorResponseDTO dto = new AuthorResponseDTO();
        dto.setId(author.getId());
        dto.setName(author.getName());

        List<String> bookTitles = author.getBooks() != null
                ? author.getBooks().stream().map(Book::getTitle).collect(Collectors.toList())
                : List.of();
        dto.setBookTitles(bookTitles);

        return dto;
    }

    // (GET All)
    @GetMapping
    public List<AuthorResponseDTO> getAll() {
        return authorRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AuthorResponseDTO getById(@PathVariable Integer id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        return mapToDTO(author);
    }

    @PostMapping
    public ResponseEntity<AuthorResponseDTO> create(@Valid @RequestBody AuthorRequestDTO requestDTO) {
        Author author = new Author();
        author.setName(requestDTO.getName());

        Author savedAuthor = authorRepository.save(author);
        return new ResponseEntity<>(mapToDTO(savedAuthor), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public AuthorResponseDTO update(@PathVariable Integer id, @Valid @RequestBody AuthorRequestDTO requestDTO) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

        existingAuthor.setName(requestDTO.getName());

        Author updatedAuthor = authorRepository.save(existingAuthor);
        return mapToDTO(updatedAuthor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

        authorRepository.delete(author);
        return ResponseEntity.noContent().build();
    }
}