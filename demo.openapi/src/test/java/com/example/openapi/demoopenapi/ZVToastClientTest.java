package com.example.openapi.demoopenapi;

import com.example.openapi.demoopenapi.connector.ZVToastClient;
import com.example.openapi.demoopenapi.connector.ZVToastUnknownPersonIdException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ZVToastClientTest {
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ResponseEntity<ZVToastClient.AuthorizationDTO> responseEntity;

    @Test
    public void shouldSuccessfullyReturn() {
        String baseUrl = "http://test";
        String testToken = "TestToken";
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        ZVToastClient.AuthorizationDTO authorizationDTO = new ZVToastClient.AuthorizationDTO();
        authorizationDTO.setAuthorization(testToken);
        when(responseEntity.getBody()).thenReturn(authorizationDTO);
        when(restTemplate.getForEntity(baseUrl, ZVToastClient.AuthorizationDTO.class)).thenReturn(responseEntity);

        ZVToastClient zvToastClient = new ZVToastClient(baseUrl, restTemplate);

        String authorizationToken = zvToastClient.getAuthorizationToken("myPersonId");

        assertThat(authorizationToken).isEqualTo("myTestToken");
    }

    @Test
    public void shouldThrowAuthorizationExceptionException() {
        String baseUrl = "http://test" + "test person id";
        when(restTemplate.getForEntity(baseUrl, ZVToastClient.AuthorizationDTO.class)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "test message"));

        ZVToastClient zvToastClient = new ZVToastClient(baseUrl, restTemplate);

        assertThatThrownBy(() -> zvToastClient.getAuthorizationToken("myPersonId")).isInstanceOf(ZVToastUnknownPersonIdException.class);
    }

}