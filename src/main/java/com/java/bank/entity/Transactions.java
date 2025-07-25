package com.java.bank.entity;

import java.time.LocalDateTime;
import java.util.Random;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
public class Transactions {

    static {
        System.out.println("Transaction");
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tid;

    private Long anumber; // Current account (for which this txn is saved/viewed)

    private String txnId; // Unique transaction ID (e.g., TXN00001234)
    private LocalDateTime localDateTime;

    private String type; // "Credit" or "Debit"
    private Double amount;
    
    private String msg; // Description (e.g., "Transfer to 1234567890")
    private Double currentBalance;

    private Long fromAccount; // Sender
    private Long toAccount;   // Receiver
    
    
    

    public Long getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(Long fromAccount) {
		this.fromAccount = fromAccount;
	}

	public Long getToAccount() {
		return toAccount;
	}

	public void setToAccount(Long toAccount) {
		this.toAccount = toAccount;
	}

	public Double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}

	// Generate txnId before save
    @PrePersist
    public void generateTxnId() {
        this.txnId = "TXN" + String.format("%08d", new Random().nextInt(100_000_000));
        this.localDateTime = LocalDateTime.now(); // Optional: auto timestamp
    }

    // Getters and Setters
    public Long getAnumber() {
        return anumber;
    }

    public void setAnumber(Long anumber) {
        this.anumber = anumber;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
