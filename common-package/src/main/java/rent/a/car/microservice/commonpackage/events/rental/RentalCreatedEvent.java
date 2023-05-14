package rent.a.car.microservice.commonpackage.events.rental;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalCreatedEvent { private UUID carId; }