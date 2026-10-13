package com.controller;

import com.dto.BookDetailsRequestDTO;
import com.dto.BookDetailsResponseDTO;
import com.model.BookDetails;
import com.repository.BookDetailsRepository;
import com.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/book-details")
public class BookDetailsController {

    @Autowired
    private BookDetailsRepository bookDetailsRepository;

    private BookDetailsResponseDTO mapToDTO(BookDetails details) {
        BookDetailsResponseDTO dto = new BookDetailsResponseDTO();
        dto.setId(details.getId());
        dto.setIsbn(details.getIsbn());
        dto.setPublicationDate(details.getPublicationDate());
        dto.setNumberOfPages(details.getNumberOfPages());
        dto.setLanguage(details.getLanguage());
        dto.setBookTitle(details.getBook() != null ? details.getBook().getTitle() : "No Book Assigned");
        return dto;
    }

    // (GET All)
    @GetMapping
    public List<BookDetailsResponseDTO> getAll() {
        return bookDetailsRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ID (GET By ID)
    @GetMapping("/{id}")
    public BookDetailsResponseDTO getById(@PathVariable Integer id) {
        BookDetails details = bookDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book Details not found with id: " + id));
        return mapToDTO(details);
    }

    // (POST)
    @PostMapping
    public ResponseEntity<BookDetailsResponseDTO> create(@Valid @RequestBody BookDetailsRequestDTO requestDTO) {
        BookDetails details = new BookDetails();
        details.setIsbn(requestDTO.getIsbn());
        details.setPublicationDate(requestDTO.getPublicationDate());
        details.setNumberOfPages(requestDTO.getNumberOfPages());
        details.setLanguage(requestDTO.getLanguage());

        BookDetails savedDetails = bookDetailsRepository.save(details);
        return new ResponseEntity<>(mapToDTO(savedDetails), HttpStatus.CREATED);
    }
   //(PUT Update)
    @PutMapping("/{id}")
    public BookDetailsResponseDTO update(@PathVariable Integer id, @Valid @RequestBody BookDetailsRequestDTO requestDTO) {
        BookDetails existingDetails = bookDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book Details not found with id: " + id));

        existingDetails.setIsbn(requestDTO.getIsbn());
        existingDetails.setPublicationDate(requestDTO.getPublicationDate());
        existingDetails.setNumberOfPages(requestDTO.getNumberOfPages());
        existingDetails.setLanguage(requestDTO.getLanguage());

        BookDetails updatedDetails = bookDetailsRepository.save(existingDetails);
        return mapToDTO(updatedDetails);
    }

    // 5. حذف تفاصيل (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        BookDetails details = bookDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book Details not found with id: " + id));

        bookDetailsRepository.delete(details);
        return ResponseEntity.noContent().build();
    }
}