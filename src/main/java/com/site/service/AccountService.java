package com.site.service;

import com.site.model.Account;
public interface AccountService{
    void createAccount(String accountId, String ownerName, double initialBalance);
    Account getAccount(String accountId);
    double deposit(String accountId, double amount);
    double withdraw(String accountId, double amount);
    void transfer(String fromAccountId, String toAccountId, double amount);
}
