package rent.a.car.microservice.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rent.a.car.microservice.inventoryservice.entities.Brand;

import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID> {}