package co.com.nubank.domain.model.transaction;

import co.com.nubank.domain.model.request.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class TempTransaction {
    private Transaction transaction;
    private double sellValue;
    private double sellAverageValue;
}
