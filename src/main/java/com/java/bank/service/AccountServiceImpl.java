package com.java.bank.service;

 import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.bank.entity.Account;
import com.java.bank.entity.Transactions;
import com.java.bank.repo.AccountRepo;
//import com.java.bank.repo.TransactionRepo;
import com.java.bank.repo.TransactionRepo;

import jakarta.transaction.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

	static {
		System.out.println("ServiceImpl Class");
	}

	@Autowired
	private AccountRepo repository;
	
	

	 @Autowired
	 private TransactionRepo transactionRepo;

	@Override
	@Transactional
	public String createAccount(Account account) {
		repository.save(account);
		Transactions transaction = new Transactions();
	    LocalDateTime currentDateTime= LocalDateTime.now();
	    
	    
	    // Transaction saving code
	    transaction.setAnumber(account.getAnumber());
	    transaction.setType("Opening DEPOSIT");
	    transaction.setAmount(account.getBalance());
	    transaction.setLocalDateTime(currentDateTime);
	    transaction.setMsg("Opning Bal");
	    transaction.setCurrentBalance(account.getBalance());
	    transactionRepo.save(transaction);
	    
		return "created";
	}

//**************************************************************************************************

	@Override
	public List<Account> getAllAccount() {

		return repository.findAll();
	}

//**************************************************************************************************

	@Override
	public Account getByAccountNo(Long anumber) {
		//return repository.findById(anumber);
		return repository.getById(anumber);
	}

//**************************************************************************************************

	/*
	 * @Override public String deposit(Long anumber, double amount) { // TODO
	 * Auto-generated method stub Optional<Account>
	 * account=repository.findById(anumber);
	 * 
	 * 
	 * 
	 * return null; }
	 */

	@Transactional
	@Override
	public String deposit(Long anumber, double amount) {

	    

		// Retrieve the account from the database using its ID
		Optional<Account> optionalAccount = repository.findById(anumber);

		if (optionalAccount.isPresent()) {
			Account account = optionalAccount.get();

			// Perform the deposit operation
			double currentBalance = account.getBalance();
			double newBalance = currentBalance + amount;
			account.setBalance(newBalance);

			// Save the updated account back to the database
			repository.save(account);
			
			
			Transactions transaction = new Transactions();
		    LocalDateTime currentDateTime= LocalDateTime.now();
		    transaction.setAnumber(anumber);
		    transaction.setType("DEPOSIT");
		    transaction.setAmount(amount);
		    transaction.setLocalDateTime(currentDateTime);
		    transaction.setMsg("by Cash");
		    transaction.setCurrentBalance(currentBalance);
		    transactionRepo.save(transaction);

			// Return a success message or any relevant information
			return "Deposit successful. New balance: " + newBalance;
		} else {
			// Handle the case where the account with the given ID is not found
			return "Account not found with ID: " + anumber;
		}
	}

//**************************************************************************************************

	@Transactional
	@Override
	public String withdraw(Long anumber, double amount) {
		Optional<Account> optionalAccount = repository.findById(anumber);
//	    
	   

		if (optionalAccount.isPresent()) {
			Account account = optionalAccount.get();

			// Check if there is sufficient balance for withdrawal
			double currentBalance = account.getBalance();
			if (currentBalance >= amount) {
				// Perform the withdrawal operation
				double newBalance = currentBalance - amount;
				account.setBalance(newBalance);

				// Save the updated account back to the database
				repository.save(account);
				
				 Transactions transaction = new Transactions();
				    
				    LocalDateTime currentDateTime= LocalDateTime.now();
				    transaction.setAnumber(anumber);
			        transaction.setType("WITHDRAW");
			        transaction.setAmount(amount);
			        transaction.setLocalDateTime(currentDateTime);
			        transaction.setMsg("Withdraw by local ATM");
			        transaction.setCurrentBalance(newBalance);
			        transactionRepo.save(transaction);

				// Return a success message or any relevant information
				return "Withdrawal successful. New balance: " + newBalance;
			} else {
				// Return an error message if there is insufficient balance
				return "Insufficient balance for withdrawal. Current balance: " + currentBalance;
			}
		} else {
			// Handle the case where the account with the given ID is not found
			return "Account not found with ID: " + anumber;
		}
	}

//**************************************************************************************************

	@Transactional
	@Override
	public String transfer(Long sourceAccountNo, Long targetAccountNo, double amount) {
		// Retrieve the source and target accounts from the database
		Optional<Account> optionalSourceAccount = repository.findById(sourceAccountNo);
		Optional<Account> optionalTargetAccount = repository.findById(targetAccountNo);

	    
        

		if (optionalSourceAccount.isPresent() && optionalTargetAccount.isPresent()) {
			Account sourceAccount = optionalSourceAccount.get();
			Account targetAccount = optionalTargetAccount.get();

			// Check if the source account has sufficient balance for the transfer
			double sourceBalance = sourceAccount.getBalance();
			if (sourceBalance >= amount) {
				// Perform the transfer
				double newSourceBalance = sourceBalance - amount;
				sourceAccount.setBalance(newSourceBalance);

				double targetBalance = targetAccount.getBalance();
				double newTargetBalance = targetBalance + amount;
				targetAccount.setBalance(newTargetBalance);

				// Save the updated accounts back to the database
				repository.save(sourceAccount);
				repository.save(targetAccount);
				
				Transactions transaction = new Transactions();
			    LocalDateTime currentDateTime= LocalDateTime.now();
			    transaction.setAnumber(sourceAccountNo);

		        transaction.setType("TRANSFER");
		        transaction.setAmount(amount);
		        transaction.setLocalDateTime(currentDateTime);
		        transaction.setMsg("Transfer to " + targetAccountNo);
		        transaction.setCurrentBalance(newSourceBalance);
		        transactionRepo.save(transaction);

				// Return a success message or any relevant information
				return "Transfer successful. "
						+ "New balance in source account: " + newSourceBalance;
			} else {
				// Return an error message if the source account has insufficient balance
				return "Insufficient balance in source account for transfer. "
						+ "Current balance: " + sourceBalance;
			}
		} else {
			// Handle the case where either the source or target account is not found
			return "Source or target account not found.";
		}
	}

//**************************************************************************************************

	@Override
	public Double getBalance(Long anumber) {
		// TODO Auto-generated method stub
		Account account = repository.getById(anumber);

		return account.getBalance();
	}

	//**************************************************************************************************
	
	public void RemoveAccount(Long anumber) {
	
		repository.deleteById(anumber);
; 
	}
	
	//**************************************************************************************************

	@Override
	public List<Transactions> getTransactionsByAccountNo(Long anumber) {
		// TODO Auto-generated method stub
 		List<Transactions> transactions=transactionRepo.getTransactionsByAccountNo(anumber);
 	 
		return  transactions;
	}
	
	public List<Transactions> getAllTransaction(){
		return transactionRepo.findAll();
	}
	
	//**************************************************************************************************
	
	
	
	

}
