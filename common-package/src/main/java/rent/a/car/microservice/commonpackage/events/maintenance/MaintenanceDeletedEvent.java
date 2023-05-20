package rent.a.car.microservice.commonpackage.events.maintenance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rent.a.car.microservice.commonpackage.events.Event;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceDeletedEvent implements Event { private UUID carId; }