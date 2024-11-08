package conrad.aiven.poc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author diego.costa on 16.02.21
 **/

@SpringBootApplication
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... arg0) throws Exception {
        System.out.println("Spring boot standalone application is up and running... Ctrl+C to stop");
        Thread.currentThread().join();
    }
}
