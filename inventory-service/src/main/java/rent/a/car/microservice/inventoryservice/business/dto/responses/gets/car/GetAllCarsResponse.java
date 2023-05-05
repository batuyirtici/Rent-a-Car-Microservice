package rent.a.car.microservice.inventoryservice.business.dto.responses.gets.car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rent.a.car.microservice.inventoryservice.entities.enums.State;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllCarsResponse {
    private UUID id;
    private UUID modelId;
    private String modelName;
    private String modelBrandName;
    private int modelYear;
    private String plate;
    private State state;
    private double dailyPrice;
}