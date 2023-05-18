package rent.a.car.microservice.rentalservice.api.clients;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import rent.a.car.microservice.commonpackage.utils.dto.ClientResponse;

import java.util.UUID;

@Slf4j
@Component
public class CarClientFallback implements CarClient {
    int i = 0;
    long lastCallTime = 0l;
    long timeDifference = 0l;

//  @Retry(name = "checkIfCarAvailable")  -->  Fault Tolerance Limiter Different Solution
    @Retryable(maxAttempts = 5, backoff = @Backoff(value = 15000))
    @Override
    public ClientResponse checkIfCarAvailable(UUID carId) {
        log.info("INVENTORY SERVICE IS DOWN!");
        throw new RuntimeException("INVENTORY-SERVICE NOT AVAILABLE RIGHT NOW!");
    }
}