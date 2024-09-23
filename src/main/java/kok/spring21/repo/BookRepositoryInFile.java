package kok.spring21.repo;

import java.util.*;

import kok.spring21.models.Book;

import org.springframework.stereotype.Component;


import java.sql.*;


@Component
public class BookRepositoryInFile implements BookRepository{
    private static int N; 

    private static String URL="jdbc:postgresql://localhost:5432/library";
    private static String USERNAME="postgres";
    private static String PASSWORD="KooKo_099";
    private static Connection connection;

    public int getN(){return N;}

    static{
        try{
            Class.forName("org.postgresql.Driver");
            connection=DriverManager.getConnection(URL,USERNAME,PASSWORD);
        }catch(Exception e){
        }
    } 


    {
        try{
            Statement s=connection.createStatement();
            ResultSet rs=s.executeQuery("select max(id) from Book");
            while(rs.next()){
                N=rs.getInt("max")+1;
            }
            
        }catch(Exception e){
            e.printStackTrace();
        } 
    }

    @Override
    public List<Book> getAllBooks(){
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
            PreparedStatement ps=connection.prepareStatement("insert into Book(id,name,author) values(?,?,?)");
            ps.setInt(1,N++); 
            ps.setString(2,book.getName());
            ps.setString(3,book.getAuthor());
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