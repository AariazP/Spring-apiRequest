package com.alejandroarias.springboot.controllers;

import com.alejandroarias.springboot.entities.Book;
import com.alejandroarias.springboot.repositories.BookRepository;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@Getter
@Setter
public class BookController {

    private final Logger log = LoggerFactory.getLogger(BookController.class);
    private BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/api/books")
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/api/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        //verificamos si el libro existe si no existe retornamos un not found (404)
        return bookOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/api/books")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        System.out.println(" Llega una petición ");
        if(book.getId() != null){
            log.warn("Trying to create a book with an id");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(bookRepository.save(book));
    }


    @PutMapping("/api/books/")
    public ResponseEntity<Book> update(@RequestBody Book book) {
        if(book.getId() == null ){ // si no tiene id quiere decir que sí es una creación
            log.warn("Trying to update a non existent book");
            return ResponseEntity.badRequest().build();
        }
        if(!bookRepository.existsById(book.getId())){
            log.warn("Trying to update a non existent book");
            return ResponseEntity.notFound().build();
        }

        // El proceso de actualización
        Book result = bookRepository.save(book);
        return ResponseEntity.ok(result); // el libro devuelto tiene una clave primaria
    }


    @DeleteMapping("/api/books/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(!bookRepository.existsById(id)){
            log.warn("Trying to delete a non existent book");
            return ResponseEntity.notFound().build();
        }
        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/api/books/")
    public ResponseEntity<Void> deleteAll() {
        bookRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

}
