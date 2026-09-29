package com.example.capestone2.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
@Service
public class WhatsappService {

    @Value("${whatsapp.instance}")
    private String instance;

    @Value("${whatsapp.token}")
    private String token;

    public void sendMessage(String phone, String text){
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.ultramsg.com/" + instance + "/messages/chat?token=" + token;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = Map.of(
                "to", "+" + phone,
                "body", text
        );

        restTemplate.postForObject(url, new HttpEntity<>(body, headers), String.class);
    }
}