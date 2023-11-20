package com.sparta.jhboardapp.controller;

public class BoardNotFoundException extends RuntimeException{

    public BoardNotFoundException(String message){
        super(message);
    }

}
