package kok.spring21.repo;

import kok.spring21.models.Book;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import lombok.ToString;

import org.springframework.stereotype.Component;

@Component
@ToString
public class BookRepositoryInMemory implements BookRepository{
    private int max=0;
    private ArrayList<Book> list=new ArrayList<>(); 

    @Override
    public void save(Book book){
        book.setId(max++);
        list.add(book);
    }

    public Book findById(int id){
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                return list.get(i);
            }
        }
        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        return list;
    }

    @Override
    public void delete(int id) {
        list.removeIf(x -> x.getId() == id);
    }

    @Override
    public Book update(Book book) {
        int idx = 0;
        int id = 0;
        boolean found=false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == (book.getId())) {
                id = book.getId();
                idx = i;
                found=true;
                break;
            }
        }
        if(!found) return null; 

        Book book1 = new Book();
        book1.setId(id);
        book1.setName(book.getName());
        book1.setAuthor(book.getAuthor());
        list.set(idx, book1);
        return book1;
    }
}