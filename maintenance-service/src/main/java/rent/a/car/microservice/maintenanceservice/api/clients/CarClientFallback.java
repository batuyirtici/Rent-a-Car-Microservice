package rent.a.car.microservice.maintenanceservice.api.clients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import rent.a.car.microservice.commonpackage.utils.dto.ClientResponse;

import java.util.UUID;

@Slf4j
@Component
public class CarClientFallback implements CarClient{
    int i = 0;
    long lastCallTime = 0l;
    long timeDifference = 0l;

    //    @Retryable(maxAttempts = 5, backoff = @Backoff(value = 5000)) //  !!! Different Solution !!!
//    @Retry(name = "checkIfCarAvailable")
    @Override
    public ClientResponse checkIfCarAvailable(UUID carId) {
        timeDifference = System.currentTimeMillis() - lastCallTime;
        log.info(++i + ". INVENTORY SERVICE IS DOWN! : " + timeDifference);
        RestTemplate rt = new RestTemplate();
        lastCallTime = System.currentTimeMillis();
        throw new RuntimeException("INVENTORY-SERVICE NOT AVAILABLE RIGHT NOW!");
    }
}
