package co.com.nubank.domain.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private String operation;
    @JsonProperty("unit-cost")
    private Integer unitCost;
    private Integer quantity;

}
