package rent.a.car.microservice.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rent.a.car.microservice.inventoryservice.entities.Car;

import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> { }