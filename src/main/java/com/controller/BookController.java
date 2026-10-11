package com.controller;

import com.model.Author;
import com.model.Category;
import com.model.Book;
import com.repository.AuthorRepository;
import com.repository.CategoryRepository;
import com.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Integer id) {
        return bookRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new com.exception.ResourceNotFoundException("Book not found with id: " + id));
    }

    // إضافة كتاب مع ربطه بالتصنيف والمؤلفين الموجودين مسبقاً
    @PostMapping
    public Book create(@RequestBody Book book) {
        // ensure of category existing
        if (book.getCategory() != null && book.getCategory().getId() != null) {
            Category category = categoryRepository.findById(book.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + book.getCategory().getId()));
            book.setCategory(category);
        }

        // ensure of authors existing
        if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
            List<Author> managedAuthors = new ArrayList<>();
            for (Author author : book.getAuthors()) {
                if (author.getId() != null) {
                    Author existingAuthor = authorRepository.findById(author.getId())
                            .orElseThrow(() -> new RuntimeException("Author not found with id: " + author.getId()));
                    managedAuthors.add(existingAuthor);
                }
            }
            book.setAuthors(managedAuthors);
        }

        return bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}