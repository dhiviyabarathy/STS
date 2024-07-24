package com.example.bank.bean;

import java.util.Date;

public interface TransactionView {

	Long getTransactionId();

	Date getDate();

	double getAmount();

	String getType();

}
