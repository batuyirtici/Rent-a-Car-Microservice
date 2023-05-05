package rent.a.car.microservice.inventoryservice.business.dto.requests.updates;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBrandRequest {
    @NotBlank
    @Size(min = 2, max = 20)
    private String name;
}