package com.site;

import com.site.config.AppConfig;
import com.site.exception.InsufficientFundsException;
import com.site.model.Account;
import com.site.service.AccountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AppTest {
    private AnnotationConfigApplicationContext context;
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        accountService = context.getBean(AccountService.class);
    }

    @AfterEach
    public void tearDown() {
        if (context != null) {
            context.close();
        }
    }

    @Test
    public void testCreateAndGetAccount() {
        accountService.createAccount("T-01", "Charlie", 300.0);
        Account account = accountService.getAccount("T-01");
        assertNotNull(account);
        assertEquals("Charlie", account.getOwnerName());
        assertEquals(300.0, account.getBalance());
    }

    @Test
    public void testDepositAndWithdraw() {
        accountService.createAccount("T-02", "David", 100.0);
        double balanceAfterDeposit = accountService.deposit("T-02", 50.0);
        assertEquals(150.0, balanceAfterDeposit);

        double balanceAfterWithdraw = accountService.withdraw("T-02", 70.0);
        assertEquals(80.0, balanceAfterWithdraw);
    }

    @Test
    public void testInsufficientFundsException() {
        accountService.createAccount("T-03", "Eve", 50.0);
        assertThrows(InsufficientFundsException.class, () -> {
            accountService.withdraw("T-03", 100.0);
        });
    }

    @Test
    public void testTransfer() {
        accountService.createAccount("T-04", "Frank", 200.0);
        accountService.createAccount("T-05", "Grace", 100.0);

        accountService.transfer("T-04", "T-05", 50.0);

        assertEquals(150.0, accountService.getAccount("T-04").getBalance());
        assertEquals(150.0, accountService.getAccount("T-05").getBalance());
    }
}
