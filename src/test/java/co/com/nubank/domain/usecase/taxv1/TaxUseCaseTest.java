package co.com.nubank.domain.usecase.taxv1;

import co.com.nubank.domain.model.request.Transaction;
import co.com.nubank.domain.model.response.TaxResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

class TaxUseCaseTest {

    @InjectMocks
    private TaxUseCase taxUseCase;

    @Test
    void shouldEstimateTax() {
        //arrange
        List<Transaction> transactionList = Arrays.asList(
                Transaction.builder()
                        .operation("buy")
                        .quantity(100)
                        .unitCost(10)
                        .build(),
                Transaction.builder()
                        .operation("buy")
                        .unitCost(15)
                        .quantity(50)
                        .build(),
                Transaction.builder()
                        .operation("sell")
                        .unitCost(15)
                        .quantity(50)
                        .build());
        //act
        Mono<TaxResponse> taxResponseMono = taxUseCase.estimateTax(transactionList);
        //assertion
        taxResponseMono.as(StepVerifier::create).verifyComplete();
    }
}