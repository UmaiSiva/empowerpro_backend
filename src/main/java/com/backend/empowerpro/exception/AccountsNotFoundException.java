package com.backend.empowerpro.exception;

public class AccountsNotFoundException extends RuntimeException{
    public AccountsNotFoundException(String message) {
        super(message);
    }
}
