package com.java.bank.service;

import java.util.List;
import java.util.Optional;

import com.java.bank.entity.Account;
import com.java.bank.entity.Transactions;
//import com.java.bank.entity.Transactions;

public interface AccountService {
	
	
	public String createAccount(Account account);
	public List<Account> getAllAccount( );
	public Account getByAccountNo(Long anumber);
	public String deposit(Long anumber,double amount);
	public String withdraw(Long anumber,double amount);
	public String transfer(Long SourceAccountNo,Long TargetAccountNo,double amount);
	public Double getBalance(Long anumber);
 	public List<Transactions> getTransactionsByAccountNo(Long anumber);
 	
}
  