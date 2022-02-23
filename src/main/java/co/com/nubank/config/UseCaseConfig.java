package co.com.nubank.config;

import co.com.nubank.domain.usecase.taxv1.TaxUseCase;
import co.com.nubank.domain.usecase.taxv2.TaxUseCase2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public TaxUseCase getTaxUseCase() {
        return new TaxUseCase();
    }

    @Bean
    public TaxUseCase2 getTaxUseCase2() {
        return new TaxUseCase2();
    }
}
