package com.example.config.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "mail")
@ConstructorBinding
public class ConfigProperties {
    private String hostName;

    public ConfigProperties(String hostName) {
        this.hostName = hostName;
    }
}
