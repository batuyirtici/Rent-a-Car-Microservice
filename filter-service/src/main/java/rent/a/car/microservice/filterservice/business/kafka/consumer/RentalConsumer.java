package rent.a.car.microservice.filterservice.business.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import rent.a.car.microservice.commonpackage.events.rental.RentalCreatedEvent;
import rent.a.car.microservice.commonpackage.events.rental.RentalDeletedEvent;
import rent.a.car.microservice.filterservice.business.abstracts.FilterService;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalConsumer {
    private final FilterService service;

    @KafkaListener(
            topics  = "rental-created",
            groupId = "filter-rental-create"
    )
    public void consume(RentalCreatedEvent event) {
        var filter = service.getByCarId(event.getCarId());
        filter.setState("Rented");
        service.add(filter);
        log.info("Rental created event consumed {}", event);
    }

    @KafkaListener(
            topics = "rental-deleted",
            groupId = "filter-rental-delete"
    )
    public void consume(RentalDeletedEvent event) {
        var filter = service.getByCarId(event.getCarId());
        filter.setState("Available");
        service.add(filter);
        log.info("Rental deleted event consumed {}", event);
    }
}