package com.controller;

import com.model.BookDetails;
import com.repository.BookDetailsRepository;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book-details")
public class BookDetailsController {
    @Autowired
    private BookDetailsRepository bookDetailsRepository;

    @GetMapping
    public ResponseEntity<List<BookDetails>> getAll() {
        List<BookDetails> books = bookDetailsRepository.findAll();

        if (books.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDetails> getById(@PathVariable Integer id) {
        return bookDetailsRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public BookDetails create(@RequestBody BookDetails bookDetails) {
        return bookDetailsRepository.save(bookDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (bookDetailsRepository.existsById(id)) {
            bookDetailsRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
