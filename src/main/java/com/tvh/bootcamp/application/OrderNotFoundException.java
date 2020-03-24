package com.tvh.bootcamp.application;

public class OrderNotFoundException extends Exception {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
