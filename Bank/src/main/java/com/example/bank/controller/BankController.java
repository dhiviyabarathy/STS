package com.example.bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bank.bean.Customer;
import com.example.bank.bean.CustomerView;
import com.example.bank.bean.Transaction;
import com.example.bank.bean.TransactionView;
import com.example.bank.dao.CustomerRepository;
import com.example.bank.service.BankService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/bank")
public class BankController {
	
	@Autowired
    private BankService bankService;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@GetMapping(value="/balance/{accountId}")
    public double showBalance(@PathVariable Long accountId) {
        return bankService.showBalance(accountId);
    }
 
    @PostMapping(value="/deposit/{accountId}/{amount}")
    public String deposit(@PathVariable Long accountId, @PathVariable double amount) {
       return bankService.deposit(accountId, amount);
    }
 
    @PostMapping(value="/withdraw/{accountId}/{amount}")
    public String withdraw(@PathVariable Long accountId, @PathVariable double amount) {
        return bankService.withdraw(accountId, amount);
    }
 
    @PostMapping(value="/transfer/{fromAccountId}/{toAccountId}/{amount}")
    public String transfer(@PathVariable Long fromAccountId, @PathVariable Long toAccountId, @PathVariable double amount) {
        return bankService.transfer(fromAccountId, toAccountId, amount);
    }
 
    @GetMapping(value="/transactions/{accountId}")
    public List<TransactionView> getLast10Transactions(@PathVariable Long accountId) {
        return bankService.getLastTenTransactions(accountId);
    }
    
    ////////JPA Practice
    
    @GetMapping("/getCustomers")
    public List<CustomerView> getCustomers() {
    	return customerRepository.getAllCustomer();
    }
    
    @GetMapping("/getCustomersDes")
    public List<CustomerView> getCustomersDes() {
    	return customerRepository.getAllCustomersInDesOrder();
    }
    @GetMapping("/getCustomersByAddress/{text}")
    public List<CustomerView> getCustomersByAddressText(@PathVariable String text) {
    	return customerRepository.getCustomerByAddressText(text);
    }
    
    @GetMapping("/getCustomerByNameAndAddress/{name}/{address}")
    public List<CustomerView> getCustomerByNameAndAddress(@PathVariable String name,@PathVariable String address) {
    	return customerRepository.getCustomerByNameAndAddress(name,address);
    }
    @CachePut(value = "customer", key = "#customer.customerId")
    @PutMapping("/addCustomer")
    public Customer putCustomer( @RequestBody Customer customer) {
        
        return customerRepository.save(customer);
    }
    
	@DeleteMapping("/deleteCustomer/{id}")
	@CacheEvict(value = "customer", key = "#id")
	public String deleteCustomer(@PathVariable Long id) {
		customerRepository.deleteById(id);
		return "success";
	}
    
    
    
    
    
    

}
