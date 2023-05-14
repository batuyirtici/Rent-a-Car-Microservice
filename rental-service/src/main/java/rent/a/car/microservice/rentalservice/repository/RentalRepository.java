package rent.a.car.microservice.rentalservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rent.a.car.microservice.rentalservice.entities.Rental;

import java.util.UUID;

public interface RentalRepository extends JpaRepository<Rental, UUID> {}