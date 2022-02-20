package co.com.nubank.config;

import co.com.nubank.domain.usecase.TaxUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public TaxUseCase getTaxUseCase() {
        return new TaxUseCase();
    }
}
