package com.example.demo.service;

public class InvalidReCaptchaException extends Throwable {
    public InvalidReCaptchaException(String response_contains_invalid_characters) {
        super(response_contains_invalid_characters);
    }
}
