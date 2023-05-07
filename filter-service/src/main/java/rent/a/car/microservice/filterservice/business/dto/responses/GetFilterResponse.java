package rent.a.car.microservice.filterservice.business.dto.responses;

import java.util.UUID;

public class GetFilterResponse {
    private UUID id;
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