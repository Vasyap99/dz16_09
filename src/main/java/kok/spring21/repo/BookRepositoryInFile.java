package kok.spring21.repo;

import java.util.*;

import kok.spring21.models.Book;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.sql.*;

import javax.annotation.PostConstruct;


import org.springframework.context.annotation.PropertySource;

@Component
@PropertySource("classpath:application.properties")
public class BookRepositoryInFile implements BookRepository{
    @Value("${kok.db.url}")
    private String URL;
    @Value("${kok.db.username}")
    private String USERNAME;
    @Value("${kok.db.password}")
    private String PASSWORD;
    private Connection connection;

    static{
        try{  //загружаем драйвер СУБД
            Class.forName("org.postgresql.Driver");
        }catch(Exception e){
        }
    } 

    @PostConstruct
    void postConstruct(){
        System.out.println(">>>POSTCONSTRUCT:");
        System.out.println(">>>"+URL);
        System.out.println(">>>"+USERNAME);
        System.out.println(">>>"+PASSWORD);
        try{ 
            System.out.println(">>>PostConstruct");
            connection=DriverManager.getConnection(URL,USERNAME,PASSWORD); 
            connection.setAutoCommit(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    {
        System.out.println(">>>CL-INIT:");
        System.out.println(">>>"+URL);
        System.out.println(">>>"+USERNAME);
        System.out.println(">>>"+PASSWORD);
    }

    @Override
    public List<Book> getAllBooks(){
        System.out.println(">>>GET_ALL_BOOKS:");
        System.out.println(">>>"+URL);
        System.out.println(">>>"+USERNAME);
        System.out.println(">>>"+PASSWORD);
        List<Book>p=new ArrayList<>();
        try{
            Statement s=connection.createStatement();
            ResultSet rs=s.executeQuery("select * from Book");
            while(rs.next()){
                Book c=new Book();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setAuthor(rs.getString("author"));
                p.add(c);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        } 
        return p; 
    }

    @Override
    public Book findById(int id){
        return getAllBooks().stream().filter( book -> book.getId()==id ).findAny().orElse(null);
    }

    @Override
    public void save(Book book){
        try{
            System.out.println(book.toString());
            PreparedStatement ps=connection.prepareStatement("insert into Book(name,author) values(?,?)");
            ps.setString(1,book.getName());
            ps.setString(2,book.getAuthor());
            System.out.println(ps);
            ps.executeUpdate(); 
        }catch(Exception e){
        } 
    }

    @Override
    public Book update(Book book) {
        try{
            PreparedStatement ps=connection.prepareStatement("update Book set name=?, author=? where id=?");
            ps.setString(1,book.getName());
            System.out.println(ps);
            ps.setString(2,book.getAuthor());
            System.out.println(ps.unwrap(org.postgresql.PGStatement.class));
            ps.setInt(3,book.getId());
            ps.executeUpdate(); 
        }catch(Exception e){
            e.printStackTrace();
        } 
        return book;
    }

    @Override
    public void delete(int id) {
        try{
            PreparedStatement ps=connection.prepareStatement("delete from Book where id=?");
            ps.setInt(1,id); 
            ps.executeQuery(); 
        }catch(Exception e){
        } 
    }

    public String toString(){
        return getAllBooks().toString();
    }
 
}