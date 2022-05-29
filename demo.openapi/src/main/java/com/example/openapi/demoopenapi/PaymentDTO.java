package com.example.openapi.demoopenapi;

public class PaymentDTO {
    private String originIban;
    private String originName;

    public String getOriginIban() {
        return originIban;
    }

    public void setOriginIban(String originIban) {
        this.originIban = originIban;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }
}
