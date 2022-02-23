package co.com.nubank.domain.usecase.taxv2;

import co.com.nubank.domain.model.exceptions.BusinessException;
import co.com.nubank.domain.model.request.Transaction;
import co.com.nubank.domain.model.response.TaxResponse;
import reactor.core.publisher.Mono;

import java.util.List;

import static co.com.nubank.domain.model.exceptions.BusinessErrorMessage.TOTAL_STOCK;

public class TaxUseCase2 {

    public Mono<TaxResponse> getTaxPayment(List<Transaction> transactionList) {
        return Mono.just(transactionList)
                .flatMap(LossOrProfitImport::estimate)
                .onErrorMap(BusinessException.class, exp -> new BusinessException(TOTAL_STOCK));
    }
}
