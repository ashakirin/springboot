package custom.autoconfig;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class MyAutoConfig {

    @Bean
    public MyTestBean createMyTestBean() {
        return new MyTestBean("test");
    }

}
