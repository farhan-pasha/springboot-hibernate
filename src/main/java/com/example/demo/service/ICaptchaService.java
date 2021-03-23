package com.example.demo.service;

import javax.servlet.http.HttpServletRequest;

public interface ICaptchaService {
    void processResponse(HttpServletRequest request, String response) throws InvalidReCaptchaException, ReCaptchaInvalidException;
}
