package com.nakao.pos.service;

import com.nakao.pos.util.EmailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Naoki Nakao on 7/24/2023
 * @project POS
 */

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final RestTemplate restTemplate;

    @Value("${email.server.url}")
    private String apiUrl;

    public void sendMail(EmailMessage emailMessage) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EmailMessage> request = new HttpEntity<>(emailMessage, headers);

        restTemplate.postForObject(apiUrl, request, String.class);
    }

}
