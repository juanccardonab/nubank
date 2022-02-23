package co.com.nubank.domain.usecase.taxv1;

import co.com.nubank.domain.model.exceptions.BusinessException;
import co.com.nubank.domain.model.request.Transaction;
import co.com.nubank.domain.model.response.Tax;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;

import static co.com.nubank.domain.model.exceptions.BusinessErrorMessage.TOTAL_STOCK;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TaxUseCaseHelper {

    public static final String BUY = "buy";
    public static final double NO_TAX_PAYMENT = 0.0;
    public static final double TAX_PERCENTAGE = 0.20;
    public static final int NON_PAYMENT_LIMIT = 20000;

    public static Mono<Double> getWeightedAveragePrice(List<Transaction> transactionList) {
        int totalStock = 0;
        double constBuyOperation = 0;
        for (Transaction transaction : transactionList) {
            if (transaction.getOperation().equals(BUY)) {
                totalStock = totalStock + transaction.getQuantity();
                constBuyOperation = constBuyOperation + (transaction.getUnitCost() * transaction.getQuantity());
            }
        }
        return totalStock > 0
                ? Mono.just(constBuyOperation / totalStock)
                : Mono.error(new BusinessException(TOTAL_STOCK));
    }

    public static void buildNoTaxResponse(List<Tax> taxList) {
        taxList.add(Tax.builder().taxValue(NO_TAX_PAYMENT).build());
    }
    public static void buildTaxResponse(List<Tax> taxList, double taxPayment) {
        taxList.add(Tax.builder().taxValue(taxPayment).build());
    }
}
