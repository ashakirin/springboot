package com.example.observabilitysample;


import io.micrometer.observation.annotation.Observed;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MyUserService {
    private static final Logger log = LoggerFactory.getLogger(MyUserService.class);

    private final Random random = new Random();

    // Example of using an annotation to observe methods
    // <user.name> will be used as a metric name
    // <getting-user-name> will be used as a span  name
    // <userType=userType2> will be set as a tag for both metric & span
//    @Observed(name = "user.name",
//            contextualName = "getting-user-name",
//            lowCardinalityKeyValues = {"userType", "userType2"})
    String userName(String userId) {
        log.info("Getting user name for user with id <{}>", userId);
        try {
            Thread.sleep(random.nextLong(200L)); // simulates latency
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "foo";
    }
}
