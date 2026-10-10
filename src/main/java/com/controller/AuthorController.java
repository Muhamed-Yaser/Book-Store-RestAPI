package com.controller;

import com.model.Author;
import com.repository.AuthorRepository;
import com.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getById(@PathVariable Integer id) {
        return authorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Author create(@RequestBody Author author) {
        return authorRepository.save(author);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }



}
