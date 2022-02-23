package co.com.nubank.domain.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@RequiredArgsConstructor
public class Tax {
    public final Double taxValue;
}
