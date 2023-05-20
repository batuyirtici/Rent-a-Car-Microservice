package rent.a.car.microservice.inventoryservice.business.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import rent.a.car.microservice.commonpackage.events.maintenance.MaintenanceCreatedEvent;
import rent.a.car.microservice.commonpackage.events.maintenance.MaintenanceDeletedEvent;
import rent.a.car.microservice.commonpackage.events.maintenance.MaintenanceReturnedEvent;
import rent.a.car.microservice.commonpackage.events.rental.RentalCreatedEvent;
import rent.a.car.microservice.commonpackage.events.rental.RentalDeletedEvent;
import rent.a.car.microservice.inventoryservice.business.abstracts.CarService;
import rent.a.car.microservice.inventoryservice.entities.enums.State;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaintenanceConsumer {
    private final CarService service;

    @KafkaListener(
            topics = "maintenance-created",
            groupId = "inventory-maintenance-create"
    )
    public void consume(MaintenanceCreatedEvent event) {
        service.changeStateByCarId(State.Maintenance, event.getCarId());
        log.info("Maintenance created event consumed {}", event);
    }

    @KafkaListener(
            topics = "maintenance-deleted",
            groupId = "inventory-maintenance-delete"
    )
    public void consume(MaintenanceDeletedEvent event) {
        service.changeStateByCarId(State.Available, event.getCarId());
        log.info("Maintenance deleted event consumed {}", event);
    }

    @KafkaListener(
            topics = "maintenance-returned",
            groupId = "inventory-maintenance-return"
    )
    public void consume(MaintenanceReturnedEvent event) {
        service.changeStateByCarId(State.Available, event.getCarId());
        log.info("Maintenance returned event consumed {}", event);
    }
}