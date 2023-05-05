package rent.a.car.microservice.inventoryservice.business.dto.requests.updates;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rent.a.car.microservice.commonpackage.utils.annotations.NotFutureYear;
import rent.a.car.microservice.commonpackage.utils.constants.Regex;
import rent.a.car.microservice.inventoryservice.entities.enums.State;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCarRequest {
    @NotNull
    private UUID modelId;

    @Min(value = 2000)
    @NotFutureYear
    private int modelYear;

    @NotBlank
    @Pattern(regexp = Regex.Plate)
    private String plate;

    @NotNull
    private State state;

    @Min(value = 1)
    private double dailyPrice;
}