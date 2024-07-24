package com.example.bank.bean;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable{
 
//	public Customer(Long customerId, String name, String address, String phoneNumber) {
//		super();
//		this.customerId = customerId;
//		this.name = name;
//		this.address = address;
//		this.phoneNumber = phoneNumber;
//	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
 
    private String name;
    private String address;
    private String phoneNumber;
 
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Account account;
    
    public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

 
}
