package co.com.nubank.intraestructure.entrypoints;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class Routes {

    @Bean
    public RouterFunction<ServerResponse> getTaxPayment(Handle handle) {
        return route()
                .POST("/v1/tax", accept(MediaType.APPLICATION_JSON), handle::getTaxPayment)
                .POST("/v2/tax", accept(MediaType.APPLICATION_JSON), handle::getTaxPaymentV2)
                .build();
    }
}
