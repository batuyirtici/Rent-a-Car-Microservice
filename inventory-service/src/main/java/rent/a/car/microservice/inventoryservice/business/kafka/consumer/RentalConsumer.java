package rent.a.car.microservice.inventoryservice.business.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import rent.a.car.microservice.commonpackage.events.rental.RentalCreatedEvent;
import rent.a.car.microservice.inventoryservice.business.abstracts.CarService;
import rent.a.car.microservice.inventoryservice.entities.enums.State;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalConsumer {
    private final CarService service;

    @KafkaListener(
            topics  = "rental-created",
            groupId = "inventory-rental-create "
    )
    public void consume(RentalCreatedEvent event) {
        service.changeStateByCarId(State.Rented, event.getCarId());
        log.info("Rental created event consumed {}", event);
    }
}
