package com.tvh.bootcamp.application;

public class EmptyOrderException extends Exception{
    public EmptyOrderException(String message){
        super(message);
    }
}
