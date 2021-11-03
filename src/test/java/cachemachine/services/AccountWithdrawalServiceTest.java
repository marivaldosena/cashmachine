package cachemachine.services;

import cachemachine.domain.CurrentAccount;
import cachemachine.domain.Withdrawal;
import cachemachine.exceptions.AmountNotValid;
import cachemachine.helpers.InsufficientBalanceArgumentsProvider;
import cachemachine.helpers.NotValidAmountsArgumentsProvider;
import cachemachine.helpers.ValidAmountsArgumentsProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountWithdrawalServiceTest {

    private AccountWithdrawalService withdrawalService;

    @BeforeEach
    void setUp() {
        withdrawalService = new AccountWithdrawalService();
    }

    @ParameterizedTest(name = "should withdraw given a valid amount")
    @ArgumentsSource(ValidAmountsArgumentsProvider.class)
    void testValidWithdraw(Integer initialBalance, Integer amountToWithdraw, Integer numberOfBallots) {
        // Arrange
        CurrentAccount account = new CurrentAccount("John Doe", new BigDecimal(initialBalance));

        // Act
        Withdrawal withdrawal = withdrawalService.withdraw(account, new BigDecimal(amountToWithdraw));

        // Assert
        assertEquals(new BigDecimal(amountToWithdraw), withdrawal.getAmount());
        assertEquals(numberOfBallots, withdrawal.getNumberOfBallots());
    }

    @ParameterizedTest(name = "should throw AmountNotValid given an invalid amount")
    @ArgumentsSource(NotValidAmountsArgumentsProvider.class)
    void testAmountNotValid(Integer initialBalance, Integer amountToWithdraw) {
        // Arrange
        CurrentAccount account = new CurrentAccount("John Doe", new BigDecimal(initialBalance));

        // Act
        var exception = assertThrows(AmountNotValid.class, () ->
            withdrawalService.withdraw(account, new BigDecimal(amountToWithdraw))
        );

        // Assert
        assertEquals("Withdrawal amount is not valid", exception.getMessage());
        assertEquals("amount-not-valid", exception.getErrorCode());
        assertEquals(AmountNotValid.class, exception.getClass());
    }

    @ParameterizedTest(name = "should throw InsufficientBalanceException given a value greater than available balance")
    @ArgumentsSource(InsufficientBalanceArgumentsProvider.class)
    void testInsufficientBalance(Integer initialBalance, Integer amountToWithdraw) {
        // Arrange
        CurrentAccount account = new CurrentAccount("John Doe", new BigDecimal(initialBalance));

        // Act
        var exception = assertThrows(RuntimeException.class, () ->
            withdrawalService.withdraw(account, new BigDecimal(amountToWithdraw))
        );

        // Assert
        assertEquals("Balance is not sufficient", exception.getMessage());
    }
}