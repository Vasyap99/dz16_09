package kok.spring21.service;

import kok.spring21.repo.*;
import kok.spring21.models.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class BookService{
    BookDAO bookDAO;

    public void save(Book book){
        bookDAO.save(book);
    }

    public List<Book> getBooks() {
        return bookDAO.getAllBooks();
    }

    public Book getBookById(int id) {
        return bookDAO.findById(id);
    }

    public void deleteBook(int id) {
        bookDAO.delete(id);
    }

    public Book updateBook(Book book) {
       return bookDAO.update(book);
    }

    public String toString(){
        return bookDAO.toString();
    }
}