package com.example.demo.service;

import com.example.demo.config.CaptchaSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestOperations;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.regex.Pattern;

@Service
public class ICaptchaServiceImpl implements ICaptchaService{
    @Autowired
    private CaptchaSettings captchaSettings;

    @Autowired
    private ReCaptchaAttemptService reCaptchaAttemptService;

    @Autowired
    private RestOperations restTemplate;

    private static Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");


    @Override
    public void processResponse (HttpServletRequest request) throws InvalidReCaptchaException, ReCaptchaInvalidException {
        String response = request.getParameter("g-recaptcha-response");

        System.out.println("Debug101:response - "+response);
        System.out.println("Debug101:getReCaptchaSecret() - "+getReCaptchaSecret());
        System.out.println("Debug101:getClientIP() - "+getClientIP(request));
        if(reCaptchaAttemptService.isBlocked(getClientIP(request))) {
            throw new InvalidReCaptchaException("Client exceeded maximum number of failed attempts");
        }

        if(!responseSanityCheck(response)) {
            throw new InvalidReCaptchaException("Response contains invalid characters");
        }

        URI verifyUri = URI.create(String.format(
                "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s",
        getReCaptchaSecret(), response, getClientIP(request)));
        GoogleResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleResponse.class);

        System.out.println("Debug101:googleResponse getHostname- "+googleResponse.getHostname());
        System.out.println("Debug101:googleResponse challenge_ts- "+googleResponse.getChallengeTs());
        System.out.println("Debug101:getErrorCodes - "+googleResponse.getErrorCodes());
        System.out.println("Debug101:isSuccess - "+googleResponse.isSuccess());
        System.out.println("Debug101:hasClientError - "+googleResponse.hasClientError());
        if(!googleResponse.isSuccess()) {
            if(googleResponse.hasClientError()) {
                reCaptchaAttemptService.reCaptchaFailed(getClientIP(request));
            }
            throw new ReCaptchaInvalidException("reCaptcha was not successfully validated");
        }

        if(!googleResponse.isSuccess()) {
            throw new ReCaptchaInvalidException("reCaptcha was not successfully validated");
        }
        reCaptchaAttemptService.reCaptchaSucceeded(getClientIP(request));

    }

    private boolean responseSanityCheck(String response) {
        return StringUtils.hasLength(response) && RESPONSE_PATTERN.matcher(response).matches();
    }

    public String getReCaptchaSecret() {
        return captchaSettings.getSecret();
    }

    private String getClientIP(HttpServletRequest request) {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

}
