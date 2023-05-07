package rent.a.car.microservice.filterservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Filter {
    @Id
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
