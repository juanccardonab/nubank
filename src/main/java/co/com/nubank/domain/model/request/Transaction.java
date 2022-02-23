package co.com.nubank.domain.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class Transaction {

    private String operation;
    @JsonProperty("unit-cost")
    private Integer unitCost;
    private Integer quantity;

}
