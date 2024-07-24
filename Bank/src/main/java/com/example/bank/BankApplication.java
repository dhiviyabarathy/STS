package com.example.bank;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import com.example.bank.bean.Customer;
import com.example.bank.dao.CustomerRepository;
@EnableCaching
@SpringBootApplication
public class BankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}
	
  @Bean
  CommandLineRunner runner(CustomerRepository repo) {
     return args -> {
        System.out.println("Initializing Bank Application...");

        Customer customer = new Customer();
        customer.setPhoneNumber("1234567890");
        customer.setAddress("Test5");
        customer.setName("Nelson");
        repo.save(customer);

        System.out.println("Bank Application Initialized Successfully!");
     };
  }
}
