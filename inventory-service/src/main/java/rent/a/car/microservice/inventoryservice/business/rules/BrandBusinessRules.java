package rent.a.car.microservice.inventoryservice.business.rules;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rent.a.car.microservice.inventoryservice.repository.BrandRepository;

import java.util.UUID;

@Service
@AllArgsConstructor
public class BrandBusinessRules {
    private final BrandRepository repository;

    public void checkIfBrandExists(UUID id) {
        if (!repository.existsById(id)) {
            // TODO: BusinessException
            throw new RuntimeException("BRAND_NOT_EXISTS");
        }
    }
}