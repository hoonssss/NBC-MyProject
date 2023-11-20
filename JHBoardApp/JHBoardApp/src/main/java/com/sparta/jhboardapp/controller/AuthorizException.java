package com.sparta.jhboardapp.controller;

public class AuthorizException extends RuntimeException{

    public AuthorizException(String message){
        super(message);
    }
}
