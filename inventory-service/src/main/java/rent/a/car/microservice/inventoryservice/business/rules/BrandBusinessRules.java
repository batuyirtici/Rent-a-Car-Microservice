package rent.a.car.microservice.inventoryservice.business.rules;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rent.a.car.microservice.commonpackage.utils.exceptions.BusinessException;
import rent.a.car.microservice.inventoryservice.repository.BrandRepository;

import java.util.UUID;

@Service
@AllArgsConstructor
public class BrandBusinessRules {
    private final BrandRepository repository;

    public void checkIfBrandExists(UUID id)
    { if (!repository.existsById(id)) throw new BusinessException("BRAND_NOT_EXISTS"); }
}