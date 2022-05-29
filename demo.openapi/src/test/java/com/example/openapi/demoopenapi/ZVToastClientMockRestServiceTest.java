package com.example.openapi.demoopenapi;

import com.example.openapi.demoopenapi.connector.ZVToastClient;
import com.example.openapi.demoopenapi.connector.ZVToastUnknownPersonIdException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.jackson.nullable.JsonNullable;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.JsonPathRequestMatchers;
import org.springframework.test.web.client.response.DefaultResponseCreator;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.times;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
class ZVToastClientMockRestServiceTest {
    private RestTemplate restTemplate;
    private MockRestServiceServer mockRestServiceServer;

    @Mock
    private ResponseEntity<ZVToastClient.AuthorizationDTO> responseEntity;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        restTemplate = new RestTemplate();
        mockRestServiceServer = MockRestServiceServer.bindTo(restTemplate).build();
    }


    @Test
    public void shouldSuccessfullyReturn() throws JsonProcessingException {
        String baseUrl = "http://test/";
        String testToken = "TestToken";
        String myPersonId = "myPersonId";
        ZVToastClient.AuthorizationDTO authorizationDTO = new ZVToastClient.AuthorizationDTO();
        authorizationDTO.setAuthorization(testToken);
        DefaultResponseCreator responseCreator = withStatus(HttpStatus.OK).body(objectMapper.writeValueAsBytes(authorizationDTO)).contentType(MediaType.APPLICATION_JSON);
        mockRestServiceServer
                .expect(times(1), requestTo(baseUrl + myPersonId))
                .andExpect(method(HttpMethod.GET))
                .andRespond(responseCreator);

        ZVToastClient zvToastClient = new ZVToastClient(baseUrl, restTemplate);

        String authorizationToken = zvToastClient.getAuthorizationToken(myPersonId);

        assertThat(authorizationToken).isEqualTo(testToken);
    }

    @Test
    public void shouldSuccessfullyReturnPost() throws JsonProcessingException {
        String baseUrl = "http://test/";
        ZVToastClient.AuthorizationDTO authorizationDTO = new ZVToastClient.AuthorizationDTO();
        String test_token = "test token";
        authorizationDTO.setAuthorization(test_token);
        DefaultResponseCreator responseCreator = withStatus(HttpStatus.OK).body(objectMapper.writeValueAsBytes(authorizationDTO)).contentType(MediaType.APPLICATION_JSON);
        mockRestServiceServer
                .expect(times(1), requestTo(baseUrl))
                .andExpect(method(HttpMethod.POST))
                .andExpect(jsonPath("$.authorization").value(test_token))
                .andRespond(responseCreator);

        ZVToastClient zvToastClient = new ZVToastClient(baseUrl, restTemplate);

        zvToastClient.postTestEntity();
    }

    public static class TestJsonNullable {
        public String normalProperty;
        public JsonNullable<String> nullableProperty;
    }

    @Test
    public void testNullableJsonProperty() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(new JsonNullableModule());

        TestJsonNullable testJsonNullable1 = new TestJsonNullable();
        testJsonNullable1.normalProperty = "normal";
        testJsonNullable1.nullableProperty = JsonNullable.of(null); //JsonNullable.undefined();

        String serialized = objectMapper.writeValueAsString(testJsonNullable1);
        System.out.println(serialized);

        TestJsonNullable testJsonNullable = objectMapper.readValue(serialized.getBytes(StandardCharsets.UTF_8), TestJsonNullable.class);
        System.out.println(testJsonNullable.nullableProperty);
    }

    @Test
    public void shouldThrowClientException() throws JsonProcessingException {
        String baseUrl = "http://test/";
        String testToken = "TestToken";
        String myPersonId = "myPersonId";
        DefaultResponseCreator responseCreator = withStatus(HttpStatus.NOT_FOUND);
        mockRestServiceServer.expect(times(1), requestTo(baseUrl + myPersonId)).andExpect(method(HttpMethod.GET)).andRespond(responseCreator);

        ZVToastClient zvToastClient = new ZVToastClient(baseUrl, restTemplate);

        assertThatThrownBy(() -> zvToastClient.getAuthorizationToken(myPersonId))
                .isInstanceOf(HttpClientErrorException.class)
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.NOT_FOUND);
    }

}