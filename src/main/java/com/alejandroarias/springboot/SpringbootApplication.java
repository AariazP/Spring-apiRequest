package com.alejandroarias.springboot;

import com.alejandroarias.springboot.entities.Book;
import com.alejandroarias.springboot.repositories.BookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class SpringbootApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(SpringbootApplication.class, args);
        BookRepository bookRepository = context.getBean(BookRepository.class);

        System.out.println(" -----------------------Inicio -------------------");
        System.out.println("El total de libros: "+bookRepository.findAll().size());

        Book book1 = new Book(null, "El principito", "Antoine de Saint-Exupéry", 100, 10.0, LocalDate.of(12, 2, 4), true);
        Book book2 = new Book(null, "Los juegos del hambre", "Suzanne Collins", 200, 20.0, LocalDate.of(12, 2, 4), true);

        System.out.println(" -----------------------Insertar -------------------");

        bookRepository.save(book1);
        bookRepository.save(book2);

        System.out.println("El total de libros: "+bookRepository.findAll().size());


        System.out.println(" -----------------------Eliminar -------------------");
        System.out.println(" Elimino un libro con el id "+book1.getId() );
       // bookRepository.deleteById(book1.getId());

        System.out.println("El total de libros: "+bookRepository.findAll().size());
    }

}
