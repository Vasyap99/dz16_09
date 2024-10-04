package kok.spring21.repo;

import java.util.*;

import kok.spring21.models.Book;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.sql.*;

import javax.annotation.PostConstruct;


@Component
public class BookRepositoryInFile implements BookRepository{
    private String URL;
    private String USERNAME;
    private String PASSWORD;
    private Connection connection;

    public BookRepositoryInFile(@Value("${kok.db.url}")String URL,
                                @Value("${kok.db.username}")String USERNAME,
                                @Value("${kok.db.password}")String PASSWORD){
        this.URL=URL;
        this.USERNAME=USERNAME;
        this.PASSWORD=PASSWORD;
        try{ 
            System.out.println(">>>PostConstruct");
            connection=DriverManager.getConnection(URL,USERNAME,PASSWORD); 
            connection.setAutoCommit(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

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
        Book b=null;
        try{
            PreparedStatement ps=connection.prepareStatement("select * from Book where id=?");
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery(); 
            if(rs.next()){
                b=new Book();
                b.setId(rs.getInt("id"));
                b.setName(rs.getString("name"));
                b.setAuthor(rs.getString("author"));
            }
            
        }catch(Exception e){
            e.printStackTrace();
        } 
        return b; 
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