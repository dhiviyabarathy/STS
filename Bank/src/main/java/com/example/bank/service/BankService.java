package com.example.bank.service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.bank.bean.Account;
import com.example.bank.bean.AccountView;
import com.example.bank.bean.Transaction;
import com.example.bank.bean.TransactionView;
import com.example.bank.dao.AccountRepository;

@Service
public class BankService {
 
    @Autowired
    private AccountRepository accountRepository;
    
    @Cacheable(value = "account",key="#accountId")
    public double showBalance(Long accountId) {
        Account account = accountRepository.findById(accountId)
                                           .orElseThrow(() -> new NoSuchElementException("Account not found"));
        return account.getBalance();
    }
    
    @CachePut(value = "account",key="#accountId")
    public String deposit(Long accountId, double amount) {
        Account account = accountRepository.findById(accountId)
                                           .orElseThrow(() -> new NoSuchElementException("Account not found"));
        account.setBalance(account.getBalance() + amount);
 
        Transaction transaction = new Transaction();
        transaction.setDate(new Date());
        transaction.setAmount(amount);
        transaction.setType("deposit");
        transaction.setAccount(account);
        account.getTransactions().add(transaction);
        accountRepository.save(account);
        return "Balance is "+account.getBalance()+ " Deposited amount is"+amount+" TransactionId is"+transaction.getTransactionId();
    }
    
    public String withdraw(Long accountId, double amount) {
        Account account = accountRepository.findById(accountId)
                                           .orElseThrow(() -> new NoSuchElementException("Account not found"));
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        account.setBalance(account.getBalance() - amount);
 
        Transaction transaction = new Transaction();
        transaction.setDate(new Date());
        transaction.setAmount(-amount);
        transaction.setType("withdrawal");
        transaction.setAccount(account);
 
        account.getTransactions().add(transaction);
        accountRepository.save(account);
        return "Balance is "+account.getBalance()+ " WithDrawn amount is"+amount+" TransactionId is"+transaction.getTransactionId();
    }
    
    public String transfer(Long fromAccountId, Long toAccountId, double amount) {
        Account fromAccount = accountRepository.findById(fromAccountId)
                                               .orElseThrow(() -> new NoSuchElementException("Account not found"));
        Account toAccount = accountRepository.findById(toAccountId)
                                             .orElseThrow(() -> new NoSuchElementException("Account not found"));
 
        if (fromAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }
 
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
 
        Transaction transactionFrom = new Transaction();
        transactionFrom.setDate(new Date());
        transactionFrom.setAmount(-amount);
        transactionFrom.setType("transfer out");
        transactionFrom.setAccount(fromAccount);
 
        Transaction transactionTo = new Transaction();
        transactionTo.setDate(new Date());
        transactionTo.setAmount(amount);
        transactionTo.setType("transfer in");
        transactionTo.setAccount(toAccount);
 
        fromAccount.getTransactions().add(transactionFrom);
        toAccount.getTransactions().add(transactionTo);
 
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        
        return "Amount of Rs."+amount+" is transfered from "+fromAccountId+" to "+toAccountId+ " with transactionId "+transactionFrom.getTransactionId();
    }
    
//    public List<Transaction> getLastTenTransactions(Long accountId) {
//        Account account = accountRepository.findById(accountId)
//                                           .orElseThrow(() -> new NoSuchElementException("Account not found"));
//        return account.getTransactions().stream()
//                      .sorted(Comparator.comparing(Transaction::getDate).reversed())
//                      .limit(10)
//                      .collect(Collectors.toList());
//    }
    @Cacheable(value = "account",key="#accountId")
    public List<TransactionView> getLastTenTransactions(Long accountId) {
    	AccountView account = accountRepository.findByAccountId(accountId);
                                           
        return account.getTransactions().stream()
                      .sorted(Comparator.comparing(TransactionView::getDate).reversed())
                      .limit(10)
                      .collect(Collectors.toList());
    }

}
