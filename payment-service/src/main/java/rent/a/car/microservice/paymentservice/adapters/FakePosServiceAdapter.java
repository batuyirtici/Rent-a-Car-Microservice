package rent.a.car.microservice.paymentservice.adapters;

import org.springframework.stereotype.Service;
import rent.a.car.microservice.paymentservice.business.abstracts.PosService;

import java.util.Random;

@Service
public class FakePosServiceAdapter implements PosService {
    @Override
    public void pay() {
        boolean isPaymentSuccessful = true;
        if (!isPaymentSuccessful) throw new RuntimeException("PAYMENT_FAILED");
    }
}