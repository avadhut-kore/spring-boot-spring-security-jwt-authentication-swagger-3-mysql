package com.tmkcomputers.springjwt.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmkcomputers.springjwt.models.Book;
import com.tmkcomputers.springjwt.service.BookService;

//mark class as Controller  
@RestController
@RequestMapping("/api/books")
public class BookController {
    // autowire the BooksService class
    @Autowired
    BookService bookService;

    // creating a get mapping that retrieves all the books detail from the database
    @GetMapping()
    private List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // creating a get mapping that retrieves the detail of a specific book
    @GetMapping("{bookid}")
    private Book getBooks(@PathVariable("bookid") int bookid) {
        return bookService.getBooksById(bookid);
    }

    // creating a delete mapping that deletes a specified book
    @DeleteMapping("{bookid}")
    private void deleteBook(@PathVariable("bookid") int bookid) {
        bookService.delete(bookid);
    }

    // creating post mapping that post the book detail in the database
    @PostMapping()
    private int saveBook(@RequestBody Book book) {
        bookService.saveOrUpdate(book);
        return book.getBookid();
    }

    // creating put mapping that updates the book detail
    @PutMapping()
    private Book update(@RequestBody Book book) {
        bookService.saveOrUpdate(book);
        return book;
    }
}