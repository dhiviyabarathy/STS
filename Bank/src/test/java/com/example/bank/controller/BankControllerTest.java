package com.example.bank.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.bank.bean.Customer;
import com.example.bank.bean.CustomerView;
import com.example.bank.dao.CustomerRepository;
import com.example.bank.service.BankService;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(BankController.class)
//@ContextConfiguration(classes=BankController.class)
public class BankControllerTest {
	
	@Autowired 
	MockMvc mockMvc;
	
	@MockBean
	BankService bankService;
	
	@MockBean
	CustomerRepository customerRepository;
	
	@Autowired
	ObjectMapper objectMapper;
	
//	@InjectMocks
//	BankController bankController;
	
	
	@Test
	void showBalanceTest() throws Exception {
		when(bankService.showBalance(Mockito.anyLong())).thenReturn((double) 1000);
		ResultActions actions=	mockMvc.perform(get("/balance/{accountId}",1001).contentType("application/json"));
		actions.andExpect(status().isOk())
		.andDo(print());
//		assertEquals(actions.andReturn(), 1000);
	}
	
	@Test
	void putCustomerTest() throws Exception {
		Customer c= new Customer();
		c.setCustomerId(1L);
		String requestBody=objectMapper.writeValueAsString(c);
		when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(new Customer());
		ResultActions actions=	mockMvc.perform(put("/addCustomer").accept("application/json").content(requestBody));
		actions.andExpect(status().isOk())
		.andDo(print());
		
	}
	
	

}
