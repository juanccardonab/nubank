package co.com.nubank.intraestructure.entrypoints;

import co.com.nubank.domain.model.exceptions.BusinessException;
import co.com.nubank.domain.model.exceptions.ErrorResponse;
import co.com.nubank.domain.model.request.RequestBody;
import co.com.nubank.domain.usecase.TaxUseCase;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class Handle {

    private final TaxUseCase taxUseCase;

    @NonNull
    public Mono<ServerResponse> getTaxPayment(ServerRequest request) {
        return request.bodyToMono(RequestBody.class)
                .map(RequestBody::getTransactionList)
                .flatMap(taxUseCase::estimateTax)
                .flatMap(response -> ServerResponse
                        .ok().contentType(APPLICATION_JSON)
                        .bodyValue(response))
                .onErrorResume(BusinessException.class, this::buildError);
    }

    private Mono<ServerResponse> buildError(BusinessException businessException) {
        return ServerResponse.badRequest()
                .contentType(APPLICATION_JSON)
                .bodyValue(ErrorResponse.builder()
                        .code(BAD_REQUEST.toString())
                        .message(businessException.getBusinessErrorMessage().getMessage())
                        .domain(businessException.getBusinessErrorMessage().getDomain())
                        .build());
    }
}
