package rent.a.car.microservice.commonpackage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rent.a.car.microservice.commonpackage.events.Event;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCarResponse implements Event {
    private UUID carId;
    private String brandName;
    private String modelName;
    private int modelYear;
    private String plate;
    private String cardHolder;
    private double dailyPrice;
    private double totalPrice;
    private int rentedForDays;
    private LocalDate rentedAt;
}