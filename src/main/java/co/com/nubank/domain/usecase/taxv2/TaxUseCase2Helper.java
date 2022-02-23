package co.com.nubank.domain.usecase.taxv2;

import co.com.nubank.domain.model.request.Transaction;
import co.com.nubank.domain.model.response.Tax;
import co.com.nubank.domain.model.transaction.TempTransaction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TaxUseCase2Helper {

    public static final String BUY = "buy";
    public static final double NO_TAX_PAYMENT = 0.0;
    public static final double TAX_PERCENTAGE = 0.20;
    public static final int NON_PAYMENT_LIMIT = 20000;

    public static void updateProfitTransaction(List<Tax> taxList, AtomicReference<Double> accumulatedLoss, TempTransaction tempTransaction) {
        double lossOrProfit = getLoos(tempTransaction) + accumulatedLoss.get();
        if (lossOrProfit <= 0) {
            accumulatedLoss.set(lossOrProfit);
            buildNoTaxResponse(taxList);
        } else {
            buildTaxResponse(taxList, lossOrProfit * TAX_PERCENTAGE);
        }
    }

    public static void updateLooseTransaction(TempTransaction tempTransaction,
                                              AtomicReference<Double> accumulatedLoss,
                                              List<Tax> taxList) {
        double loos = getLoos(tempTransaction);
        accumulatedLoss.set(accumulatedLoss.get() + loos);
        buildNoTaxResponse(taxList);
    }

    public static double getLoos(TempTransaction tempTransaction) {
        return tempTransaction.getSellValue() - tempTransaction.getSellAverageValue();
    }

    public static double getSellValue(Transaction transaction) {
        return (double) transaction.getQuantity() * transaction.getUnitCost();
    }

    public static Map<Integer, TempTransaction> buildTransactionMap(List<Transaction> transactionList) {
        int position = 0;
        double averagePrice = getAveragePrice(transactionList);
        Map<Integer, TempTransaction> transactionMap = new HashMap<>();
        for (Transaction transaction : transactionList) {
            position++;
            transactionMap.put(position, TempTransaction.builder()
                    .transaction(transaction)
                    .sellValue(getSellValue(transaction))
                    .sellAverageValue(getSellAveragePrice(transaction, averagePrice))
                    .build());
        }
        return transactionMap;
    }

    public static double getSellAveragePrice(Transaction transaction, double averagePrice) {
        return transaction.getQuantity() * averagePrice;
    }

    public static void buildNoTaxResponse(List<Tax> taxList) {
        taxList.add(Tax.builder().taxValue(NO_TAX_PAYMENT).build());
    }

    public static void buildTaxResponse(List<Tax> taxList, double taxPayment) {
        taxList.add(Tax.builder().taxValue(taxPayment).build());
    }

    public static double getAveragePrice(List<Transaction> transactionList) {
        Predicate<Transaction> isBuyOperation = value -> value.getOperation().equals(BUY);
        double sellValue = transactionList.stream().filter(isBuyOperation)
                .mapToDouble(TaxUseCase2Helper::getSellValue).sum();
        int totalStock = transactionList.stream().filter(isBuyOperation)
                .mapToInt(Transaction::getQuantity).sum();
        return sellValue / totalStock;
    }
}
