package com.example.democontroller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeepStubsTest {

    @Test
    void deepStubs() {
        var client = mock(RestClient.class, RETURNS_DEEP_STUBS);

        when(client.get().uri(anyString()).retrieve().body(eq(String.class))).thenReturn("my response");

        String response = client.get()
                .uri("http://example/foo/bar")
                .retrieve()
                .body(String.class);

        assertThat(response).isEqualTo("my response");
    }
}
