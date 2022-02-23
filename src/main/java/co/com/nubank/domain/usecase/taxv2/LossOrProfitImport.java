package co.com.nubank.domain.usecase.taxv2;

import co.com.nubank.domain.model.request.Transaction;
import co.com.nubank.domain.model.response.Tax;
import co.com.nubank.domain.model.response.TaxResponse;
import co.com.nubank.domain.model.transaction.TempTransaction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static co.com.nubank.domain.usecase.taxv1.TaxUseCaseHelper.buildNoTaxResponse;
import static co.com.nubank.domain.usecase.taxv2.TaxUseCase2Helper.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LossOrProfitImport {

    public static Mono<TaxResponse> estimate(List<Transaction> transactionList) {
        List<Tax> taxList = new ArrayList<>();
        Map<Integer, TempTransaction> transactionMap = buildTransactionMap(transactionList);
        estimateTaxPayment(taxList, transactionMap);
        return Mono.just(TaxResponse.builder()
                .taxList(taxList)
                .build());
    }

    private static void estimateTaxPayment(List<Tax> taxList, Map<Integer, TempTransaction> transactionMap) {
        AtomicReference<Double> accumulatedLoss = new AtomicReference<>(0.0);
        transactionMap.forEach((value, tempTransaction) -> {
            if (tempTransaction.getTransaction().getOperation().equals(BUY)) {
                buildNoTaxResponse(taxList);
            } else {
                if (tempTransaction.getSellValue() <= NON_PAYMENT_LIMIT) {
                    updateLooseTransaction(tempTransaction, accumulatedLoss, taxList);
                } else {
                    if (tempTransaction.getSellValue() <= tempTransaction.getSellAverageValue()) {
                        updateLooseTransaction(tempTransaction, accumulatedLoss, taxList);
                    } else {
                        updateProfitTransaction(taxList, accumulatedLoss, tempTransaction);
                    }
                }
            }
        });
    }
}
