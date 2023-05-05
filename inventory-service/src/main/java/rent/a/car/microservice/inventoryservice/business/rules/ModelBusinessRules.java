package rent.a.car.microservice.inventoryservice.business.rules;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rent.a.car.microservice.inventoryservice.repository.BrandRepository;
import rent.a.car.microservice.inventoryservice.repository.ModelRepository;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ModelBusinessRules {
    private final ModelRepository repository;

    public void checkIfModelExists(UUID id) {
        if (!repository.existsById(id)) {
            // TODO: BusinessException
            throw new RuntimeException("MODEL_NOT_EXISTS");
        }
    }
}