package br.com.magalu.config.exception.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomErrorResponse {
    private String errorCode;
    private String errorMessage;
    private String errorType;
}
