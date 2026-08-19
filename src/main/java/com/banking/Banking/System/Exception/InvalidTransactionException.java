package com.banking.Banking.System.Exception;

import jakarta.persistence.criteria.CriteriaBuilder;

public class InvalidTransactionException extends RuntimeException{
    public InvalidTransactionException(String message){
        super(message);
    }
}
