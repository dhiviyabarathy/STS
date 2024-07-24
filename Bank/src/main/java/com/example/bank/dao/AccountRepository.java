package com.example.bank.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bank.bean.Account;
import com.example.bank.bean.AccountView;

import java.util.List;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	
	Optional<Account> findById(Long id);
	AccountView findByAccountId(Long accountId);
	Account save(Account acc);
} 
