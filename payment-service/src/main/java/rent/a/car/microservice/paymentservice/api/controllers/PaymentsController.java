package rent.a.car.microservice.paymentservice.api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rent.a.car.microservice.commonpackage.dto.CreateRentalPaymentRequest;
import rent.a.car.microservice.commonpackage.utils.dto.ClientResponse;
import rent.a.car.microservice.paymentservice.business.abstracts.PaymentService;
import rent.a.car.microservice.paymentservice.business.dto.requests.CreatePaymentRequest;
import rent.a.car.microservice.paymentservice.business.dto.requests.UpdatePaymentRequest;
import rent.a.car.microservice.paymentservice.business.dto.responses.CreatePaymentResponse;
import rent.a.car.microservice.paymentservice.business.dto.responses.GetAllPaymentsResponse;
import rent.a.car.microservice.paymentservice.business.dto.responses.GetPaymentResponse;
import rent.a.car.microservice.paymentservice.business.dto.responses.UpdatePaymentResponse;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/payments")
public class PaymentsController {
    private final PaymentService service;

    @GetMapping
    List<GetAllPaymentsResponse> getAll()
    { return service.getAll(); }

    @GetMapping("/{id}")
    GetPaymentResponse getById(@PathVariable UUID id)
    { return service.getById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreatePaymentResponse add(@Valid @RequestBody CreatePaymentRequest request)
    { return service.add(request); }

    @PutMapping("/{id}")
    UpdatePaymentResponse update(@PathVariable UUID id, @Valid @RequestBody UpdatePaymentRequest request)
    { return service.update(id, request); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable UUID id)
    { service.delete(id); }

    @PostMapping("/payment-validation")
    ClientResponse processRentalPayment(@RequestBody CreateRentalPaymentRequest request)
    { return service.processRentalPayment(request); }
}