package com.example.openapi.demoopenapi;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@EnableScheduling
@TestPropertySource(properties = {"statusjob.delay=1000"})
class SampleSchedulingTest {
    @SpyBean
    private SampleScheduling sampleScheduling;

    @Test
    public void test() {
        await()
                .atMost(1001, TimeUnit.MILLISECONDS)
                .untilAsserted(() -> verify(sampleScheduling, atLeast(1)).scheduledIncrement());

    }

}