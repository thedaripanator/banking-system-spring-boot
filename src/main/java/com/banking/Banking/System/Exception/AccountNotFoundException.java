package com.banking.Banking.System.Exception;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(String message){
        super(message);
    }
}
