package rent.a.car.microservice.rentalservice.api.clients.payment;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import rent.a.car.microservice.commonpackage.dto.CreateRentalPaymentRequest;
import rent.a.car.microservice.commonpackage.utils.dto.ClientResponse;

@FeignClient(name = "payment-service", fallback = PaymentClientFallback.class)
public interface PaymentClient {
    @PostMapping(value = "api/payments/payment-validation")
    ClientResponse paymentValidation(@RequestBody CreateRentalPaymentRequest request);
}