package cachemachine.controllers;

import cachemachine.domain.CurrentAccount;
import cachemachine.domain.Withdrawal;
import cachemachine.exceptions.AmountNotValid;
import cachemachine.helpers.NotValidAmountsArgumentsProvider;
import cachemachine.helpers.ValidAmountsArgumentsProvider;
import cachemachine.requests.WithdrawalRequest;
import cachemachine.services.BalanceService;
import cachemachine.services.WithdrawalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WithdrawalController.class)
public class WithdrawalControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private WithdrawalService withdrawalService;

    @MockBean
    private BalanceService accountBalanceService;

    @ParameterizedTest(name = "should withdraw given a valid request")
    @ArgumentsSource(ValidAmountsArgumentsProvider.class)
    void shouldWithdrawCashGivenAValidNumber(Integer initialBalance, Integer amountToWithdraw, Integer numberOfBallots) throws Exception {
        // Arrange
        CurrentAccount account = new CurrentAccount("John Doe", new BigDecimal(initialBalance));
        Withdrawal withdrawal = new Withdrawal(numberOfBallots, BigDecimal.valueOf(amountToWithdraw));
        WithdrawalRequest request = new WithdrawalRequest(new BigDecimal(amountToWithdraw));

        Mockito.when(accountBalanceService.getAccountById(any())).thenReturn(account);
        Mockito.when(withdrawalService.withdraw(account, request.getAmount())).thenReturn(withdrawal);

        // Act
        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/current-account/1/withdraw")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(request))
            ).andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.cashRequested", Matchers.is(amountToWithdraw)))
            .andExpect(jsonPath("$.numberOfBallots", Matchers.is(numberOfBallots)));

        // Verify
        Mockito.verify(accountBalanceService).getAccountById(any());
        Mockito.verify(withdrawalService).withdraw(any(), any());
    }

    @ParameterizedTest(name = "should throw AmountNotValid given an invalid amount")
    @ArgumentsSource(NotValidAmountsArgumentsProvider.class)
    void testAmountNotValid(Integer initialBalance, Integer amountToWithdraw) throws Exception {
        // Arrange
        CurrentAccount account = new CurrentAccount("John Doe", new BigDecimal(initialBalance));
        WithdrawalRequest request = new WithdrawalRequest(BigDecimal.valueOf(amountToWithdraw));

        Mockito.when(accountBalanceService.getAccountById(any())).thenReturn(account);
        Mockito.when(withdrawalService.withdraw(any(), any())).thenThrow(new AmountNotValid("Withdrawal amount is not valid"));

        // Act
        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/current-account/1/withdraw")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(request))
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.*.message").isNotEmpty())
            .andExpect(jsonPath("$.*.errorCode", Matchers.hasItems("amount-not-valid")));

        // Verify
        Mockito.verify(accountBalanceService).getAccountById(any());
        Mockito.verify(withdrawalService).withdraw(any(), any());
    }
}
