package com.example.openapi.demoopenapi;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="restInterface1", url = "http://locahost:8080/")
public interface TestFeinClient extends TestRestInterface {
}
