package rent.a.car.microservice.invoiceservice.business.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllInvoicesResponse {
    private String id;
    private UUID carId;
    private String modelName;
    private String brandName;
    private String cardHolder;
    private String plate;
    private int modelYear;
    private double dailyPrice;
    private int rentedForDays;
    private double totalPrice;
    private LocalDate rentedAt;
}
