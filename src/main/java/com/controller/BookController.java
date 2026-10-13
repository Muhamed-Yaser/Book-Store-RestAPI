package com.controller;

import com.dto.BookRequestDTO;
import com.dto.BookResponseDTO;
import com.model.Author;
import com.model.Book;
import com.model.Category;
import com.repository.AuthorRepository;
import com.repository.BookRepository;
import com.repository.CategoryRepository;
import com.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    //Dependency Injection
    //BookRepository: Interface to talk to DB
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private BookResponseDTO mapToDTO(Book book) {
        BookResponseDTO dto = new BookResponseDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setPrice(book.getPrice());

        dto.setCategoryName(book.getCategory() != null ? book.getCategory().getName() : "No Category");

        List<String> authorNames = book.getAuthors() != null
                ? book.getAuthors().stream().map(Author::getName).collect(Collectors.toList())
                : List.of();
        dto.setAuthorNames(authorNames);

        return dto;
    }

    // (GET All with Pagination)
    @GetMapping
    public ResponseEntity<Page<BookResponseDTO>> getAllBooks(@PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<Book> bookPage = bookRepository.findAll(pageable);

        Page<BookResponseDTO> responseDTOPage = bookPage.map(this::mapToDTO);

        return ResponseEntity.ok(responseDTOPage);
    }

    //(GET By ID)
    @GetMapping("/{id}")
    public BookResponseDTO getBookById(@PathVariable Integer id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return mapToDTO(book);
    }

    // (Add book)
    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@Valid @RequestBody BookRequestDTO requestDTO) {
        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + requestDTO.getCategoryId()));

        List<Author> authorsList = authorRepository.findAllById(requestDTO.getAuthorIds());
        if (authorsList == null || authorsList.isEmpty()) {
            throw new ResourceNotFoundException("No valid authors found for the provided IDs.");
        }


        Book book = new Book();
        book.setTitle(requestDTO.getTitle());
        book.setPrice(requestDTO.getPrice());
        book.setCategory(category);
        book.setAuthors(authorsList);


        Book savedBook = bookRepository.save(book);

        return new ResponseEntity<>(mapToDTO(savedBook), HttpStatus.CREATED);
    }

    // (Update)
    @PutMapping("/{id}")
    public BookResponseDTO updateBook(@PathVariable Integer id, @Valid @RequestBody BookRequestDTO requestDTO) {

        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));


        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + requestDTO.getCategoryId()));


        List<Author> authorsList = authorRepository.findAllById(requestDTO.getAuthorIds());
        if (authorsList == null || authorsList.isEmpty()) {
            throw new ResourceNotFoundException("No valid authors found for the provided IDs.");
        }

        existingBook.setTitle(requestDTO.getTitle());
        existingBook.setPrice(requestDTO.getPrice());
        existingBook.setCategory(category);
        existingBook.setAuthors(authorsList);

        Book updatedBook = bookRepository.save(existingBook);

        return mapToDTO(updatedBook);
    }

    // (DELETE)
    @DeleteMapping("/{id}")
    //no return value
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        bookRepository.delete(book);

        //response : 204 No Content
        return ResponseEntity.noContent().build();
    }
}