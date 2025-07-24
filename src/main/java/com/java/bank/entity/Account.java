package com.java.bank.entity;

import java.util.Random;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Account {

    @Id
    private Long anumber;
    private String name;
    private String username;
    private String address;
    private Double balance = 00.00;
    private String password;

    static {
        System.out.println("Account Class");
    }

    // === Constructor to auto-generate account number ===
    public Account(String name, String username, String address, Double balance, String password) {
        this.anumber = generateAccountNumber();
        this.name = name;
        this.username = username;
        this.address = address;
        this.balance = balance;
        this.password = password;
    }

    // === Default Constructor ===
    public Account() {
        this.anumber = generateAccountNumber();
    }

    // === Auto-generate account number: starts with 9245 + 6 random digits ===
    private Long generateAccountNumber() {
        String prefix = "9245";
        int suffix = new Random().nextInt(1_000_000); // range 000000 to 999999
        String suffixStr = String.format("%06d", suffix);
        return Long.parseLong(prefix + suffixStr);
    }

    // === Getters and Setters ===
    public Long getAnumber() {
        return anumber;
    }

    public void setAnumber(Long anumber) {
        this.anumber = anumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // === toString ===
    @Override
    public String toString() {
        return "Account [anumber=" + anumber + ", name=" + name + ", username=" + username +
               ", address=" + address + ", balance=" + balance + ", password=" + password + "]";
    }
}
