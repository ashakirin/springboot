package conrad.aiven.poc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;

/**
 * @author diego.costa on 18.02.21
 **/

@Configuration
public class ConnectionProperties {

    @Value("${kafka.broker}")
    private String broker;

    @Value("${kafka.schema-register}")
    private String schemaRegister;

    @Value("${kafka.ssl.keystore.password}")
    private String keystorePassword;

    @Value("${kafka.ssl.key.password}")
    private String keyPassword;

    @Value("classpath:client.truststore.jks")
    Resource truststoreFile;

    @Value("classpath:client.keystore.p12")
    Resource keystoreFile;

    @Value("${kafka.user.username}")
    private String kafkaUserUsername;

    @Value("${kafka.user.password}")
    private String kafkaUserPassword;

    public Properties buildProperties() throws IOException {
        Properties config = new Properties();

        config.put(BOOTSTRAP_SERVERS_CONFIG, broker);
        config.put(SCHEMA_REGISTRY_URL_CONFIG, String.format(schemaRegister, kafkaUserUsername, kafkaUserPassword));
        config.put("security.protocol", "SSL");
        config.put("ssl.endpoint.identification.algorithm", "");
        config.put("ssl.truststore.location", truststoreFile.getFile().getAbsolutePath());
        config.put("ssl.truststore.password", keystorePassword);
        config.put("ssl.keystore.type", "PKCS12");
        config.put("ssl.keystore.location", keystoreFile.getFile().getAbsolutePath());
        config.put("ssl.keystore.password", keystorePassword);
        config.put("ssl.key.password", keyPassword);
        config.put("basic.auth.credentials.source", "USER_INFO");
        config.put("basic.auth.user.info", String.format("%s:%s", kafkaUserUsername, kafkaUserPassword));
        return config;
    }
}
