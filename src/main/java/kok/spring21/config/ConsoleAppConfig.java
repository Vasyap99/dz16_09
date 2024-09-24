package kok.spring21.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan("kok.spring21")           //skanirovanie proishodit takzhe vo vlozhennyh papkah rekursivno
@PropertySource("classpath:application.properties")
public class ConsoleAppConfig{ 
  //Used in addition of @PropertySource
  @Bean
  public static PropertySourcesPlaceholderConfigurer   propertySourcesPlaceholderConfigurer() {
      return new PropertySourcesPlaceholderConfigurer();
  }
}