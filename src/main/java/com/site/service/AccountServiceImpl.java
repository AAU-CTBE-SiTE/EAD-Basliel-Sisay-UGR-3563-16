package com.site.service;

import com.site.exception.InsufficientFundsException;
import com.site.model.Account;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AccountServiceImpl implements AccountService{
    private final Map<String, Account> accounts = new ConcurrentHashMap<>();
    @Override
    public void createAccount(String accountId, String ownerName, double initialBalance) {
        if(accounts.containsKey(accountId)){
            throw new IllegalArgumentException("Account already exists with ID: " + accountId);
        }
        accounts.put(accountId, new Account(accountId, ownerName, initialBalance));
    }
    @Override
    public Account getAccount(String accountId){
        Account account = accounts.get(accountId);
        if (account == null){
            throw new IllegalArgumentException("Account not found: " + accountId);
        }
        return account;
    }
    @Override
    public double deposit(String accountId, double amount){
        if (amount <= 0){
            throw new IllegalArgumentException("Deposit amount must be greater than zero");
        }
        Account account = getAccount(accountId);
        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);
        return newBalance;
    }
    @Override
    public double withdraw(String accountId, double amount){
        if (amount <= 0){
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero");
        }
        Account account = getAccount(accountId);
        if (account.getBalance() < amount){
            throw new InsufficientFundsException(
                    "Insufficient funds in account " + accountId + ". Available: " + account.getBalance() + ", Requested: " + amount
            );
        }
        double newBalance = account.getBalance() - amount;
        account.setBalance(newBalance);
        return newBalance;
    }
    @Override
    public void transfer(String fromAccountId, String toAccountId, double amount){
        withdraw(fromAccountId, amount);
        deposit(toAccountId, amount);
    }
}
