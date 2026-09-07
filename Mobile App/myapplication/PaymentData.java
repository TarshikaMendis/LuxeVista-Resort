package com.example.myapplication;

public class PaymentData {
    public String serviceName, servicePrice, cardNumber, expirationDate, cvv, postalCode;

    public PaymentData(String serviceName, String servicePrice, String cardNumber, String expirationDate, String cvv, String postalCode) {
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.postalCode = postalCode;
    }
}
