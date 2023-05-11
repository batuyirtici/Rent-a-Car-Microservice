package rent.a.car.microservice.inventoryservice.business.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import rent.a.car.microservice.commonpackage.events.BrandDeletedEvent;
import rent.a.car.microservice.commonpackage.events.CarCreatedEvent;
import rent.a.car.microservice.commonpackage.events.CarDeletedEvent;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(CarCreatedEvent event) {
        log.info(String.format("car-created event => %s", event.toString()));
        Message<CarCreatedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, "car-created")
                .build();

        kafkaTemplate.send(message);
    }

    public void sendMessage(CarDeletedEvent event) {
        log.info(String.format("car-deleted event => %s", event.toString()));
        Message<CarDeletedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, "car-deleted")
                .build();

        kafkaTemplate.send(message);
    }

    public void sendMessage(BrandDeletedEvent event) {
        log.info(String.format("brand-deleted event => %s", event.toString()));
        Message<BrandDeletedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, "brand-deleted")
                .build();

        kafkaTemplate.send(message);
    }
}
