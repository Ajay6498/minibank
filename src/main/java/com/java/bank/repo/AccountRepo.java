package com.java.bank.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.java.bank.entity.Account;
 
public interface AccountRepo extends JpaRepository<Account, Long> {
    //This Query is incomplete
	//@Query("SELECT * FROM ACCOUNT WHERE  username ")
	//Account findByUserName(@Param("username") String username);
	
	
	@Query("SELECT a FROM Account a WHERE a.username = :username")
	Account findByUserName(@Param("username") String username);

	
  

}
