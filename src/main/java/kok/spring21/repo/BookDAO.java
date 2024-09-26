package kok.spring21.repo;

import java.util.*;

import kok.spring21.models.Book;
import kok.spring21.models.Library;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.sql.*;

import javax.annotation.PostConstruct;

@Component
public class BookDAO{
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

    public Book findById(int id){
        return getAllBooks().stream().filter( book -> book.getId()==id ).findAny().orElse(null);
    }

    public void save(Book book){
        try{
            PreparedStatement ps=connection.prepareStatement("insert into Book(name,author) values(?,?)");
            ps.setString(1,book.getName());
            ps.setString(2,book.getAuthor());
            ps.executeUpdate(); 
        }catch(Exception e){
        } 
    }

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

    public void delete(int id) {
        try{
            connection.setAutoCommit(false);
            PreparedStatement p1=connection.prepareStatement("delete from Mapping where book_id=?");
            p1.setInt(1,id); 
            System.out.println(p1);
            p1.executeUpdate(); 

            PreparedStatement ps=connection.prepareStatement("delete from Book where id=?");
            ps.setInt(1,id); 
            ps.executeUpdate(); 

            connection.commit();
        }catch(Exception e){
            e.printStackTrace(); 
            try{connection.rollback();}catch(Exception e1){}
        }finally{ 
            try{connection.setAutoCommit(true);}catch(Exception e1){}
        }
    }

    /// 
    public List<Book> listLibrary(int id) {
        List<Book>p=new ArrayList<>();
        try{
            PreparedStatement ps=connection.prepareStatement("select * from Book  inner join Mapping on Book.id=Mapping.book_id  inner join Library on Library.id=Mapping.library_id  where Library.id=?");
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery(); 
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
    public void addToLibrary(int id,int id1) {
        try{
            PreparedStatement ps=connection.prepareStatement("insert into Mapping(library_id,book_id) values(?,?)");
            ps.setInt(1,id);
            ps.setInt(2,id1);
            ps.executeUpdate(); 
        }catch(Exception e){
        }         
    }
    public void removeFromLibrary(int id,int id1) {
        try{
            PreparedStatement ps=connection.prepareStatement("delete from Mapping where library_id=? and book_id=?");
            ps.setInt(1,id);
            ps.setInt(2,id1);
            System.out.println(ps);
            ps.executeUpdate(); 
        }catch(Exception e){
        }
    }

    public void saveLib(Library l){
        try{
            PreparedStatement ps=connection.prepareStatement("insert into Library(addr) values(?)");
            ps.setString(1,l.getAddr());
            ps.executeUpdate(); 
        }catch(Exception e){
        } 
    }

    public void deleteLib(int id) {
        try{
            connection.setAutoCommit(false);
            PreparedStatement p1=connection.prepareStatement("delete from Mapping where library_id=?");
            p1.setInt(1,id); 
            System.out.println(p1);
            p1.executeUpdate(); 

            PreparedStatement ps=connection.prepareStatement("delete from Library where id=?");
            ps.setInt(1,id); 
            ps.executeUpdate(); 

            connection.commit();
        }catch(Exception e){
            e.printStackTrace(); 
            try{connection.rollback();}catch(Exception e1){}
        }finally{ 
            try{connection.setAutoCommit(true);}catch(Exception e1){}
        }
    }

    public List<Library> getAllLibs(){
        List<Library>p=new ArrayList<>();
        try{
            Statement s=connection.createStatement();
            ResultSet rs=s.executeQuery("select * from Library");
            while(rs.next()){
                Library c=new Library();
                c.setId(rs.getInt("id"));
                c.setAddr(rs.getString("addr"));
                p.add(c);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        } 
        return p; 
    }

    public String toString(){
        return getAllBooks().toString();
    }
 
}