package com.java.bank.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.java.bank.entity.Account;
import com.java.bank.entity.Transactions;

public interface TransactionRepo extends JpaRepository<Transactions, Long> {
	
	 @Query("SELECT t FROM Transactions t WHERE t.anumber = :anumber")
	    List<Transactions> getTransactionsByAccountNo(@Param("anumber") Long anumber);
	 
	 


}
