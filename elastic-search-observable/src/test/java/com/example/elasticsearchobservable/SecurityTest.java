package com.example.elasticsearchobservable;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.web.server.ServerHttpSecurity;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class SecurityTest {
    @Mock
    ServerHttpSecurity serverHttpSecurity;

    @Test
    public void test() {
        // serverHttpSecurity = mock(ServerHttpSecurity.class, CALLS_REAL_METHODS);
        when(serverHttpSecurity.build()).thenReturn(null);
        serverHttpSecurity.build();

        ServerHttpSecurity serverHttpSecurity1 = new MyServerHttpSecurity();
    }

    private static class MyServerHttpSecurity extends ServerHttpSecurity {
        public MyServerHttpSecurity() {
            super();
        }
    }
}
