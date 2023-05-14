package rent.a.car.microservice.rentalservice.api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rent.a.car.microservice.rentalservice.business.abstracts.RentalService;
import rent.a.car.microservice.rentalservice.business.dto.requests.CreateRentalRequest;
import rent.a.car.microservice.rentalservice.business.dto.requests.UpdateRentalRequest;
import rent.a.car.microservice.rentalservice.business.dto.responses.CreateRentalResponse;
import rent.a.car.microservice.rentalservice.business.dto.responses.GetAllRentalsResponse;
import rent.a.car.microservice.rentalservice.business.dto.responses.GetRentalResponse;
import rent.a.car.microservice.rentalservice.business.dto.responses.UpdateRentalResponse;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/rentals")
public class RentalsController {
    private final RentalService service;

    @GetMapping
    public List<GetAllRentalsResponse> getall()
    { return service.getAll(); }

    @GetMapping("/{id}")
    public GetRentalResponse getById(@PathVariable UUID id)
    { return service.getById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateRentalResponse add(@Valid @RequestBody CreateRentalRequest request)
    { return service.add(request); }

    @PutMapping("/{id}")
    public UpdateRentalResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateRentalRequest request)
    { return service.update(id, request); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id)
    { service.delete(id); }
}