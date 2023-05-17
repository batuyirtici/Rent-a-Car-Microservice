package rent.a.car.microservice.rentalservice.business.rules;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rent.a.car.microservice.commonpackage.utils.exceptions.BusinessException;
import rent.a.car.microservice.rentalservice.repository.RentalRepository;
import rent.a.car.microservice.rentalservice.api.clients.CarClient;

import java.util.UUID;

@Service
@AllArgsConstructor
public class RentalBusinessRules {
    private final RentalRepository repository;
    private final CarClient carClient;

    public void checkIfRentalExists(UUID id)
    { if (!repository.existsById(id)) throw new BusinessException("RENTAL_NOT_EXISTS"); }

    public void ensureCarIsAvailable(UUID carId) {
        var response = carClient.checkIfCarAvailable(carId);
        if (!response.isSuccess()) {
            throw new BusinessException(response.getMessage());
        }
    }
}