package co.com.nubank.intraestructure.entrypoints;

import co.com.nubank.domain.model.request.RequestBody;
import co.com.nubank.domain.usecase.TaxUseCase;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handle {

    private final TaxUseCase taxUseCase;

    @NonNull
    public Mono<ServerResponse> getTaxPayment(ServerRequest request) {
        return request.bodyToMono(RequestBody.class)
                .onErrorMap(Exception.class, ex -> new Exception(ex.getMessage()))
                .map(RequestBody::getTransactionList)
                .flatMap(taxUseCase::settleTax)
                .flatMap(response ->
                        ServerResponse
                                .ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response));
    }
}
