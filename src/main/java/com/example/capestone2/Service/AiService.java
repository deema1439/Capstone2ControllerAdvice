package com.example.capestone2.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class AiService {

    @Value("${ai.api.key}")
    private String apiKey;

    public String ask(String prompt) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", apiKey);
        headers.set("anthropic-version", "2023-06-01");

        Map<String, Object> body = Map.of(
                "model", "claude-haiku-4-5-20251001",
                "max_tokens", 300,
                "messages", List.of(Map.of("role", "user", "content", prompt))
        );

        Map response = restTemplate.postForObject("https://api.anthropic.com/v1/messages",
                new HttpEntity<>(body, headers), Map.class);

        List<Map> content = (List<Map>) response.get("content");
        return (String) content.get(0).get("text");
    }


}

