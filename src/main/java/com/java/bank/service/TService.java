package com.java.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.bank.entity.Transactions;
import com.java.bank.repo.TransactionRepo;

@Service
public class TService  {
	
	@Autowired
	private TransactionRepo repo;
	
	public String addTransaction(Transactions transaction) {
		
		Transactions tr=repo.save(transaction);
		return "added";
	}
	
	
	public List<Transactions> getTransaction(){
		return repo.findAll();
	}
	

}
