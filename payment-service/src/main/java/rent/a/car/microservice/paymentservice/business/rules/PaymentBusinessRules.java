package rent.a.car.microservice.paymentservice.business.rules;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rent.a.car.microservice.commonpackage.dto.CreateRentalPaymentRequest;
import rent.a.car.microservice.commonpackage.utils.exceptions.BusinessException;
import rent.a.car.microservice.paymentservice.business.dto.requests.CreatePaymentRequest;
import rent.a.car.microservice.paymentservice.repository.PaymentRepository;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentBusinessRules {
    private final PaymentRepository repository;

    public void checkIfPaymentExists(UUID id) {
        if (!repository.existsById(id)) throw new BusinessException("PAYMENT_NOT_EXISTS");

    }

    public void checkIfBalanceIsEnough(double price, double balance) {
        if (balance < price) throw new BusinessException("PAYMENT_NOT_ENOUGH_MONEY");

    }

    public void checkIfCardExists(CreatePaymentRequest request) {
        if (repository.existsByCardNumber(request.getCardNumber()))
            throw new BusinessException("CARD_NUMBER_ALREADY_EXISTS");
    }

    public void checkIfPaymentIsValid(CreateRentalPaymentRequest request) {
        if (!repository.existsByCardNumberAndCardHolderAndCardExpirationYearAndCardExpirationMonthAndCardCvv(
                request.getCardNumber(),
                request.getCardHolder(),
                request.getCardExpirationYear(),
                request.getCardExpirationMonth(),
                request.getCardCvv()
        )) {
            throw new BusinessException("NOT_VALID_PAYMENT");
        }
    }
}