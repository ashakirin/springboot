package com.example.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

public class FooClientController {
    @Value("${resourceserver.api.url}")
    private String fooApiUrl;

    @Autowired
    private WebClient webClient;

    @GetMapping("/foos")
    public String getFoos(Model model) {
        List<FooModel> foos = this.webClient.get()
                .uri(fooApiUrl)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<FooModel>>() {
                })
                .block();
        model.addAttribute("foos", foos);
        return "foos";
    }
}
}
