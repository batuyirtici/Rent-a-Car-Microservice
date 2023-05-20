package rent.a.car.microservice.maintenanceservice.business.rules;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rent.a.car.microservice.commonpackage.utils.exceptions.BusinessException;
import rent.a.car.microservice.maintenanceservice.api.clients.CarClient;
import rent.a.car.microservice.maintenanceservice.repository.MaintenanceRepository;

import java.util.UUID;

@Service
@AllArgsConstructor
public class MaintenanceBusinessRules {
    private final MaintenanceRepository repository;
    private final CarClient carClient;


    public void checkIfMaintenanceExists(UUID id)
    { if (!repository.existsById(id)) throw new BusinessException("MAINTENANCE_NOT_EXISTS"); }

    public void checkIfCarUnderMaintenance(UUID carId) {
        if (repository.existsByCarIdAndIsCompletedIsFalse(carId))
            throw new BusinessException("CAR_IS_CURRENTLY_UNDER_MAINTENANCE");
    }

    public void checkIfCarIsNotUnderMaintenance(UUID carId) {
        if (!repository.existsByCarIdAndIsCompletedIsFalse(carId))
            throw new BusinessException("CAR_NOT_REGISTERED_FOR_MAINTENANCE");
    }

    public void checkIfCarAvailable(UUID carId) {
        var response = carClient.checkIfCarAvailable(carId);
        if (!response.isSuccess()) throw new BusinessException(response.getMessage());
    }
}