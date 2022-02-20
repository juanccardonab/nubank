package co.com.nubank.domain.usecase;

import co.com.nubank.domain.model.exceptions.BusinessException;
import co.com.nubank.domain.model.request.Transaction;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;

import static co.com.nubank.domain.model.exceptions.BusinessErrorMessage.TOTAL_STOCK;

public class TaxUseCase {

    public Mono<Long> estimateTax(List<Transaction> transactionList) {
        return getWeightedAveragePrice(transactionList);
    }

    private Predicate<Long> should

    private Mono<Long> getWeightedAveragePrice(List<Transaction> transactionList) {
        int totalStock = 0;
        long constBuyOperation = 0;
        for (Transaction transaction : transactionList) {
            totalStock = totalStock + transaction.getQuantity();
            constBuyOperation = constBuyOperation + ((long) transaction.getUnitCost() * transaction.getQuantity());
        }
        return totalStock > 0
                ? Mono.just(constBuyOperation / totalStock)
                : Mono.error(new BusinessException(TOTAL_STOCK));
    }
}
