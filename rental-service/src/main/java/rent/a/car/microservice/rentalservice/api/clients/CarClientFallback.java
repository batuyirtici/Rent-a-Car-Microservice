package rent.a.car.microservice.rentalservice.api.clients;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import rent.a.car.microservice.commonpackage.utils.dto.ClientResponse;

import java.util.UUID;

@Slf4j
@Component
public class CarClientFallback implements CarClient {
    @Override
    public ClientResponse checkIfCarAvailable(UUID carId) {
        log.info("INVENTORY SERVICE IS DOWN!");
        throw new RuntimeException("INVENTORY-SERVICE NOT AVAILABLE RIGHT NOW!");
    }
}