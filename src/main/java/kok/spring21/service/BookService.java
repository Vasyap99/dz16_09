package kok.spring21.service;

import kok.spring21.repo.*;
import kok.spring21.models.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

//@AllArgsConstructor
@Service
public class BookService{

    @Autowired
    @Qualifier("bookRepositoryInFile")
    private BookRepository bookRepo;

    public void save(Book book){
        bookRepo.save(book);
    }

    public List<Book> getBooks() {
        return bookRepo.getAllBooks();
    }

    public Book getBookById(int id) {
        return bookRepo.findById(id);
    }

    public void deleteBook(int id) {
        bookRepo.delete(id);
    }

    public Book updateBook(Book book) {
       return bookRepo.update(book);
    }

    public String toString(){
        return bookRepo.toString();
    }
}