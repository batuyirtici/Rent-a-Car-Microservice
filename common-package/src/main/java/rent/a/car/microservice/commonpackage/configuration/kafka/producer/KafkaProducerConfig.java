package rent.a.car.microservice.commonpackage.configuration.kafka.producer;

import rent.a.car.microservice.commonpackage.kafka.producer.KafkaProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaProducerConfig {
    @Bean
    KafkaProducer getKafkaProducer(KafkaTemplate<String, Object> kafkaTemplate)
    { return new KafkaProducer(kafkaTemplate); }
}