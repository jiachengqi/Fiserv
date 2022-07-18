package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {

    private final RestTemplate restTemplate;

    public RestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getPostsAsString(String input) {
        String url = "https://en.wikipedia.org/w/api.php?action=parse&section=0&prop=text&format=json&page={input}";
        String result = null;

        try {
            ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class, input);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            result = String.valueOf(jsonNode.get("parse").get("text"));
        } catch (NullPointerException ignored) {
        } catch (JsonProcessingException e) {
            System.out.println("Internal error, couldn't parse JSON object");
        }
        return result;
    }

    public int count(String str, String target) {
        return (str.length() - str.replace(target, "").length()) / target.length();
    }
}
