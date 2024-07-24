package com.example.bank.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bank.bean.Customer;
import com.example.bank.bean.CustomerView;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	@Query(value="SELECT * FROM Customer",nativeQuery = true)//sql native query
	List<CustomerView> getAllCustomer();
	
	@Query("SELECT c FROM Customer c WHERE c.address LIKE :text") //JPQL with Param
	List<CustomerView> getCustomerByAddressText(@Param ("text") String addressText);
	
	@Query("SELECT c FROM Customer c ORDER BY c.name DESC")
	List<CustomerView> getAllCustomersInDesOrder();
	
	@Query("SELECT c FROM Customer c WHERE c.name = :name AND c.address = :address")
	List<CustomerView> getCustomerByNameAndAddress(@Param("name")String name,@Param("address")String address);

	Customer save(Customer customer);
	
}
