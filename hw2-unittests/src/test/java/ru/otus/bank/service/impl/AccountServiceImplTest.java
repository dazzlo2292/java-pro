package ru.otus.bank.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.bank.dao.AccountDao;
import ru.otus.bank.entity.Account;
import ru.otus.bank.entity.Agreement;
import ru.otus.bank.service.exception.AccountException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {
    @Mock
    AccountDao accountDao;

    @InjectMocks
    AccountServiceImpl accountServiceImpl;

    @Test
    public void testTransfer() {
        Account sourceAccount = new Account();
        sourceAccount.setAmount(new BigDecimal(100));

        Account destinationAccount = new Account();
        destinationAccount.setAmount(new BigDecimal(10));

        when(accountDao.findById(eq(1L))).thenReturn(Optional.of(sourceAccount));
        when(accountDao.findById(eq(2L))).thenReturn(Optional.of(destinationAccount));

        accountServiceImpl.makeTransfer(1L, 2L, new BigDecimal(10));

        assertEquals(new BigDecimal(90), sourceAccount.getAmount());
        assertEquals(new BigDecimal(20), destinationAccount.getAmount());
    }

    @Test
    public void testSourceNotFound() {
        when(accountDao.findById(any())).thenReturn(Optional.empty());

        AccountException result = assertThrows(AccountException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                accountServiceImpl.makeTransfer(1L, 2L, new BigDecimal(10));
            }
        });
        assertEquals("No source account", result.getLocalizedMessage());
    }

    @Test
    public void testDestinationNotFound() {
        Account sourceAccount = new Account();

        when(accountDao.findById(eq(1L))).thenReturn(Optional.of(sourceAccount));
        when(accountDao.findById(eq(2L))).thenReturn(Optional.empty());

        AccountException result = assertThrows(AccountException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                accountServiceImpl.makeTransfer(1L, 2L, new BigDecimal(10));
            }
        });
        assertEquals("No destination account", result.getLocalizedMessage());
    }

    @Test
    public void testTransferWithVerify() {
        Account sourceAccount = new Account();
        sourceAccount.setAmount(new BigDecimal(100));
        sourceAccount.setId(1L);

        Account destinationAccount = new Account();
        destinationAccount.setAmount(new BigDecimal(10));
        destinationAccount.setId(2L);

        when(accountDao.findById(eq(1L))).thenReturn(Optional.of(sourceAccount));
        when(accountDao.findById(eq(2L))).thenReturn(Optional.of(destinationAccount));

        ArgumentMatcher<Account> sourceMatcher =
                argument -> argument.getId().equals(1L) && argument.getAmount().equals(new BigDecimal(90));

        ArgumentMatcher<Account> destinationMatcher =
                argument -> argument.getId().equals(2L) && argument.getAmount().equals(new BigDecimal(20));

        accountServiceImpl.makeTransfer(1L, 2L, new BigDecimal(10));

        verify(accountDao).save(argThat(sourceMatcher));
        verify(accountDao).save(argThat(destinationMatcher));
    }


    @Test
    public void testAddAccount() {
        Agreement agreement = new Agreement();
        agreement.setId(1L);

        accountServiceImpl.addAccount(agreement, "111", 0, new BigDecimal(100));

        ArgumentMatcher<Account> accountMatcher =
                argument ->
                        argument.getAgreementId().equals(1L)
                                && argument.getNumber().equals("111")
                                && argument.getType().equals(0)
                                && argument.getAmount().equals(new BigDecimal(100));

        verify(accountDao).save(argThat(accountMatcher));
    }

    @Test
    public void testGetAccounts() {
        Account account = new Account();
        account.setId(1L);

        when(accountDao.findAll()).thenReturn(List.of(account));

        List<Account> result = accountServiceImpl.getAccounts();
        assertFalse(result.isEmpty());
    }

    @Test
    public void testGetAccountsByAgreement() {
        Account account = new Account();
        account.setId(1L);

        Agreement agreement = new Agreement();
        agreement.setId(11L);

        when(accountDao.findByAgreementId(eq(11L))).thenReturn(List.of(account));

        List<Account> result = accountServiceImpl.getAccounts(agreement);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testCharge() {
        Account account = new Account();
        account.setId(1L);
        account.setAmount(new BigDecimal(100));

        when(accountDao.findById(eq(1L))).thenReturn(Optional.of(account));

        ArgumentMatcher<Account> accountMatcher =
                argument -> argument.getAmount().equals(new BigDecimal(99));

        boolean result = accountServiceImpl.charge(account.getId(), new BigDecimal(1));

        verify(accountDao).save(argThat(accountMatcher));
        assertTrue(result);
    }

}
