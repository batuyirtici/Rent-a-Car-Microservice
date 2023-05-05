package rent.a.car.microservice.inventoryservice.business.dto.responses.gets.brand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetBrandResponse {
    private UUID id;
    private String name;
}