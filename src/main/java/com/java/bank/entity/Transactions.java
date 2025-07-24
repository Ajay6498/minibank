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

    private Long anumber;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tid;

    private String txnId; //  New user-friendly 8-digit transaction ID

    private LocalDateTime localDateTime;
    private String type;
    private Double amount;
    private String msg;
    private Double currentBalance;

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
