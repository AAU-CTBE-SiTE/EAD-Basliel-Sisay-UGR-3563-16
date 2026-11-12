package com.site;

import com.site.config.AppConfig;
import com.site.exception.InsufficientFundsException;
import com.site.service.AccountService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App{
    public static void main(String[] args){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        AccountService accountService = context.getBean(AccountService.class);
        accountService.createAccount("ACC-101", "Alice", 500.0);
        accountService.createAccount("ACC-102", "Bob", 200.0);
        accountService.deposit("ACC-101", 150.0);
        accountService.transfer("ACC-101", "ACC-102", 100.0);

        System.out.println("Alice's Account: " + accountService.getAccount("ACC-101"));
        System.out.println("Bob's Account: " + accountService.getAccount("ACC-102"));

        try{
            accountService.withdraw("ACC-102", 1000.0);
        } catch (InsufficientFundsException e){
            System.out.println("[Main Handler] Handled expected exception: " + e.getMessage());
        }
        context.close();
    }
}
