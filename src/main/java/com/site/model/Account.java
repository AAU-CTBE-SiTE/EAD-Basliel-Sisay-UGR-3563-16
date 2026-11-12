package com.site.model;
public class Account{
    private String accountId;
    private String ownerName;
    private double balance;
    public Account(String accountId, String ownerName, double initialBalance){
        this.accountId = accountId;
        this.ownerName = ownerName;
        this.balance = initialBalance;
    }
    public String getAccountId(){
        return accountId;
    }
    public String getOwnerName(){
        return ownerName;
    }
    public double getBalance(){
        return balance;
    }
    public void setBalance(double balance){
        this.balance = balance;
    }
    @Override
    public String toString(){
        return "Account{" + "accountId='" + accountId + '\'' + ", ownerName='" + ownerName + '\'' + ", balance=" + balance + '}';
    }
}
