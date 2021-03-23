package com.example.demo.service;

public class ReCaptchaInvalidException extends Throwable {
    public ReCaptchaInvalidException(String reCaptcha_was_not_successfully_validated) {
        super(reCaptcha_was_not_successfully_validated);

    }
}
