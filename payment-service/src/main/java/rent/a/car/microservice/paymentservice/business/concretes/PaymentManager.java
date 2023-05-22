package rent.a.car.microservice.paymentservice.business.concretes;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rent.a.car.microservice.commonpackage.dto.CreateRentalPaymentRequest;
import rent.a.car.microservice.commonpackage.utils.dto.ClientResponse;
import rent.a.car.microservice.commonpackage.utils.exceptions.BusinessException;
import rent.a.car.microservice.commonpackage.utils.mappers.ModelMapperService;
import rent.a.car.microservice.paymentservice.business.abstracts.PaymentService;
import rent.a.car.microservice.paymentservice.business.abstracts.PosService;
import rent.a.car.microservice.paymentservice.business.dto.requests.CreatePaymentRequest;
import rent.a.car.microservice.paymentservice.business.dto.requests.UpdatePaymentRequest;
import rent.a.car.microservice.paymentservice.business.dto.responses.CreatePaymentResponse;
import rent.a.car.microservice.paymentservice.business.dto.responses.GetAllPaymentsResponse;
import rent.a.car.microservice.paymentservice.business.dto.responses.GetPaymentResponse;
import rent.a.car.microservice.paymentservice.business.dto.responses.UpdatePaymentResponse;
import rent.a.car.microservice.paymentservice.business.rules.PaymentBusinessRules;
import rent.a.car.microservice.paymentservice.entities.Payment;
import rent.a.car.microservice.paymentservice.repository.PaymentRepository;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentManager implements PaymentService {
    private final PosService posService;
    private final ModelMapperService mapper;
    private final PaymentBusinessRules rules;
    private final PaymentRepository repository;

    @Override
    public List<GetAllPaymentsResponse> getAll() {
        List<Payment> payments = repository.findAll();

        List<GetAllPaymentsResponse> responses = payments
                .stream()
                .map(payment -> mapper.forResponse().map(payment, GetAllPaymentsResponse.class))
                .toList();

        return responses;
    }

    @Override
    public GetPaymentResponse getById(UUID id) {
        rules.checkIfPaymentExists(id);

        Payment payment = repository.findById(id).orElseThrow();

        GetPaymentResponse response = mapper.forResponse().map(payment, GetPaymentResponse.class);

        return response;
    }

    @Override
    public CreatePaymentResponse add(CreatePaymentRequest request) {
        rules.checkIfCardExists(request);

        Payment payment = mapper.forRequest().map(request, Payment.class);

        payment.setId(UUID.randomUUID());
        repository.save(payment);

        CreatePaymentResponse response = mapper.forResponse().map(payment, CreatePaymentResponse.class);

        return response;
    }

    @Override
    public UpdatePaymentResponse update(UUID id, UpdatePaymentRequest request) {
        rules.checkIfPaymentExists(id);

        Payment payment = mapper.forRequest().map(request, Payment.class);

        payment.setId(id);
        repository.save(payment);

        UpdatePaymentResponse response = mapper.forResponse().map(payment, UpdatePaymentResponse.class);

        return response;
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfPaymentExists(id);

        repository.deleteById(id);
    }

    @Override
    public ClientResponse processRentalPayment(CreateRentalPaymentRequest request) {
        ClientResponse response = new ClientResponse();

        try {
            rules.checkIfPaymentIsValid(request);

            var payment = repository.findByCardNumber(request.getCardNumber());

            rules.checkIfBalanceIsEnough(request.getPrice(), payment.getBalance());

            posService.pay();

            payment.setBalance(payment.getBalance() - request.getPrice());
            repository.save(payment);

            response.setSuccess(true);
        } catch (BusinessException exception) {
            response.setSuccess(false);
            response.setMessage(exception.getMessage());
        }

        return response;
    }
}