package co.com.nubank.domain.model.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessErrorMessage {
    TOTAL_STOCK("Total stocks should be grater than 0", "Tax estimation");
    private final String message;
    private final String domain;
}
