package co.com.nubank.domain.usecase;

import co.com.nubank.domain.model.request.Transaction;
import reactor.core.publisher.Mono;

import java.util.List;

public class TaxUseCase {

    public Mono<String> settleTax(List<Transaction> transaction) {
        return Mono.just("useCaseReturn");
    }
}
