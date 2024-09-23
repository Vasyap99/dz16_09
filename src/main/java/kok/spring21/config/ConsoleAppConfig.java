package kok.spring21.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan("kok.spring21")           //skanirovanie proishodit takzhe vo vlozhennyh papkah rekursivno
public class ConsoleAppConfig{ 
}