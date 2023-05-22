package rent.a.car.microservice.rentalservice.api.clients.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import rent.a.car.microservice.commonpackage.dto.CreateRentalPaymentRequest;
import rent.a.car.microservice.commonpackage.utils.dto.ClientResponse;

@Slf4j
@Component
//@EnableScheduling
public class PaymentClientFallback implements PaymentClient{

    @Retryable(maxAttempts = 5, backoff = @Backoff(value = 5000))
    @Override
    public ClientResponse paymentValidation(CreateRentalPaymentRequest request) {
        log.info("PAYMENT SERVICE IS DOWN!");
        throw new RuntimeException("PAYMENT SERVICE IS DOWN!");
    }
}