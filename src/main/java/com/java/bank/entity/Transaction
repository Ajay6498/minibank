package com.java.bank.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Transactions {
	
	static {
		
		System.out.println("Transaction");
	}
	
	private Long anumber;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tid;
	private LocalDate date;
	private String type;
	private Double amount;
	
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
 
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
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
}
