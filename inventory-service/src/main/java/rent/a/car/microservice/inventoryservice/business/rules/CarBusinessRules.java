package rent.a.car.microservice.inventoryservice.business.rules;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rent.a.car.microservice.commonpackage.utils.exceptions.BusinessException;
import rent.a.car.microservice.inventoryservice.entities.enums.State;
import rent.a.car.microservice.inventoryservice.repository.CarRepository;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CarBusinessRules {
    private final CarRepository repository;

    public void checkIfCarExists(UUID id)
    { if (!repository.existsById(id)) throw new BusinessException("CAR_NOT_EXISTS"); }

    public void checkCarAvailability(UUID id) {
        var car = repository.findById(id).orElseThrow();

        if (!car.getState().equals(State.Available)) throw new BusinessException("CAR_NOT_AVAILABLE");
    }
}