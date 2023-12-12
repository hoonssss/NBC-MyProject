package com.example.backendgram.exception;

public class NewsfeedNotFoundException extends RuntimeException{

    public NewsfeedNotFoundException(String message) {
        super(message);
    }
}
