package rent.a.car.microservice.commonpackage.events.rental;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import lombok.*;
import rent.a.car.microservice.commonpackage.events.Event;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalCreatedEvent implements Event { private UUID carId; }
