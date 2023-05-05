package rent.a.car.microservice.inventoryservice.business.dto.responses.updates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateModelResponse {
    private UUID id;
    private UUID brandId;
    private String name;
}