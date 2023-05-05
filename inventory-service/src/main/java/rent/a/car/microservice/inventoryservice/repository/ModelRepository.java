package rent.a.car.microservice.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rent.a.car.microservice.inventoryservice.entities.Model;

import java.util.UUID;

public interface ModelRepository extends JpaRepository<Model, UUID>{}