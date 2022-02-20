package co.com.nubank.domain.model.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BusinessException extends Exception {
    private final BusinessErrorMessage businessErrorMessage;
}
