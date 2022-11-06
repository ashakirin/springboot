package com.example.demospringapp;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

public class MySmoke {

    @Test
    public void testFirst() {
        throw new RuntimeException("test");
    }

    @Test
    public void dateTest() {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse("2018-12-30T06:00Z");
        System.out.println(offsetDateTime);
    }
}
