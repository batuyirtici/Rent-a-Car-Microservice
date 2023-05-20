package rent.a.car.microservice.maintenanceservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rent.a.car.microservice.maintenanceservice.entities.Maintenance;

import java.util.UUID;

public interface MaintenanceRepository extends JpaRepository<Maintenance, UUID> {
    Maintenance findMaintenanceByCarIdAndIsCompletedFalse(UUID carId);

    boolean existsByCarIdAndIsCompletedIsFalse(UUID carId);
}