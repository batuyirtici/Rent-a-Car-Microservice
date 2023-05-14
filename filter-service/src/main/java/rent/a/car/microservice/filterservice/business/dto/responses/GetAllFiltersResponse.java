package rent.a.car.microservice.filterservice.business.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllFiltersResponse {
    private String id;
    private UUID brandId;
    private UUID carID;
    private UUID modelId;
    private String brandName;
    private String modelName;
    private String plate;
    private int modelYear;
    private double dailyPrice;
    private String state;
}