package com.example.bank.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bank.bean.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {}
