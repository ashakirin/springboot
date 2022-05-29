package com.example.openapi.demoopenapi.connector;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ZVToastClient {
    private final String baseUrl;
    private final RestTemplate restTemplate;

    public ZVToastClient(@Value("${connector.zvtoast.baseurl}") String baseUrl, RestTemplate restTemplate) {
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
    }

    public String getAuthorizationToken(String personId) {
        ResponseEntity<AuthorizationDTO> forEntity = restTemplate.getForEntity(baseUrl + personId, AuthorizationDTO.class);
        return forEntity.getBody().getAuthorization();
    }

    public void postTestEntity() {
        AuthorizationDTO authorizationDTO = new AuthorizationDTO();
        authorizationDTO.setAuthorization("test token");
        restTemplate.postForEntity(baseUrl, authorizationDTO, AuthorizationDTO.class);
    }

    public static class AuthorizationDTO {
        private String authorization;

        public String getAuthorization() {
            return authorization;
        }

        public void setAuthorization(String authorization) {
            this.authorization = authorization;
        }
    }
}
