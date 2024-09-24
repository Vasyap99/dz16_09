package kok.spring21;

//import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import kok.spring21.repo.*;
import kok.spring21.config.*;
import kok.spring21.models.*;
import kok.spring21.service.*;

import java.util.Scanner;
import java.util.List;


public class TestSpring {
    public static void main(String[] args) {
        //ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        AnnotationConfigApplicationContext context= new AnnotationConfigApplicationContext(ConsoleAppConfig.class);

        BookService bookService=context.getBean("bookService",BookService.class);


        Scanner s=new Scanner(System.in);
        int id=0,id1=0;
        Book b=null;
        String n=null,a=null;
        while(true){
            System.out.println("-".repeat(50));
            System.out.println(bookService.toString());
            System.out.println("<Enter command: C R U D LL(list L) LA(add B to L) LR(remove B from L) LC(create Lib) LD(delete L), E for exit>");
                String i=s.nextLine();
                switch(i){
                    case "C":
                        System.out.println("-Enter name:");
                        n=s.nextLine();
                        System.out.println("-Enter author:");
                        a=s.nextLine();
                        bookService.save(Book.builder()
                             .name(n)
                             .author(a)
                             .build() 
                        );
                        break;
                    case "R":
                        System.out.println("-Enter book id:");
                        id=Integer.parseInt(s.nextLine());
                        b=bookService.getBookById(id);
                        System.out.println(b!=null ? b.toString() : "null");
                        break;
                    case "U":
                        System.out.println("-Enter book id:");
                        id=Integer.parseInt(s.nextLine());
                        System.out.println("-Enter name:");
                        n=s.nextLine();
                        System.out.println("-Enter author:");
                        a=s.nextLine();
                        Book bk=new Book(id,n,a);
                        System.out.println(bk);
                        bookService.updateBook(bk);                        
                        break;
                    case "D":
                        System.out.println("-Enter book id:");
                        id=Integer.parseInt(s.nextLine());
                        bookService.deleteBook(id);                       
                        break;
                    case "LL":
                        System.out.println("-Enter lib id:");
                        id=Integer.parseInt(s.nextLine());
                        List<Book> l = bookService.listLibrary(id);         
                        System.out.println(l.toString());              
                        break;
                    case "LA":
                        System.out.println("-Enter lib id:");
                        id=Integer.parseInt(s.nextLine());
                        System.out.println("-Enter book id:");
                        id1=Integer.parseInt(s.nextLine());
                        bookService.addToLibrary(id,id1);         
                        break;
                    case "LR":
                        System.out.println("-Enter lib id:");
                        id=Integer.parseInt(s.nextLine());
                        System.out.println("-Enter book id:");
                        id1=Integer.parseInt(s.nextLine());
                        bookService.removeFromLibrary(id,id1);         
                        break;
                    case "E":
                        context.close();
                        return;
                    default:
                        ;
                }
        }

        //context.close();
    }
}
