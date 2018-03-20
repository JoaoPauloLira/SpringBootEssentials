package br.com.jsilva.awesome.error;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CustomErrorType {
    private String erroMsg;

    public CustomErrorType(String erroMsg) {
        this.erroMsg = erroMsg;
    }
}