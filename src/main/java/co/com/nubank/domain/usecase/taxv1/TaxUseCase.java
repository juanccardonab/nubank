package co.com.nubank.domain.usecase.taxv1;

import co.com.nubank.domain.model.exceptions.BusinessException;
import co.com.nubank.domain.model.request.Transaction;
import co.com.nubank.domain.model.response.Tax;
import co.com.nubank.domain.model.response.TaxResponse;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static co.com.nubank.domain.model.exceptions.BusinessErrorMessage.TOTAL_STOCK;
import static co.com.nubank.domain.usecase.taxv1.TaxUseCaseHelper.*;

public class TaxUseCase {

    public Mono<TaxResponse> estimateTax(List<Transaction> transactionList) {
        return getWeightedAveragePrice(transactionList)
                .map(getTaxPayment(transactionList))
                .onErrorMap(BusinessException.class, exp -> new BusinessException(TOTAL_STOCK));
    }

    private Function<Double, TaxResponse> getTaxPayment(List<Transaction> transactionList) {
        List<Tax> taxList = new ArrayList<>();
        return average -> {
            double lossOrProfitValue = 0.0;
            for (Transaction transaction : transactionList) {
                if (transaction.getOperation().equals(BUY)) {
                    buildNoTaxResponse(taxList);
                } else {
                    lossOrProfitValue = taxImportCalculation(average, lossOrProfitValue, transaction, taxList);
                }
            }
            return TaxResponse.builder()
                    .taxList(taxList)
                    .build();
        };
    }

    private double taxImportCalculation(Double average, double accumulatedLoss,
                                        Transaction transaction, List<Tax> taxList) {
        double sellValue = (double) transaction.getQuantity() * transaction.getUnitCost();
        double sellAverage = transaction.getQuantity() * average;
        if (sellValue <= NON_PAYMENT_LIMIT) {
            double loss = sellValue - sellAverage;
            accumulatedLoss = accumulatedLoss + loss;
            buildNoTaxResponse(taxList);
        } else {
            if (sellValue <= sellAverage) {
                double loss = sellValue - sellAverage;
                accumulatedLoss = accumulatedLoss + loss;
                buildNoTaxResponse(taxList);
            } else {
                double totalSellValue = sellValue - sellAverage;
                double lossOrProfit = totalSellValue + accumulatedLoss;
                if (lossOrProfit <= 0) {
                    accumulatedLoss = lossOrProfit;
                    buildNoTaxResponse(taxList);
                } else {
                    double taxPayment = lossOrProfit * TAX_PERCENTAGE;
                    buildTaxResponse(taxList, taxPayment);
                }
            }
        }
        return accumulatedLoss;
    }
}