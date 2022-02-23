package co.com.nubank.intraestructure.entrypoints;

import co.com.nubank.domain.model.response.TaxResponse;
import co.com.nubank.domain.usecase.taxv1.TaxUseCase;
import co.com.nubank.domain.usecase.taxv2.TaxUseCase2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class HandleTest {

    @InjectMocks
    private Handle handle;

    @Mock
    private TaxUseCase taxUseCase;

    @Mock
    private TaxUseCase2 taxUseCase2;

    @BeforeAll
    void setUp(){
    }

    @Test
    void shouldGetTaxPayment() {
        // arange
        ServerRequest serverRequest = Mockito.mock(ServerRequest.class);
        handle.getTaxPaymentV2(serverRequest)
                .as(StepVerifier::create)
                .assertNext(serverResponse -> {
                    Assertions.assertThat(serverResponse).isInstanceOf(TaxResponse.class);
                })
                .verifyComplete();
    }
}