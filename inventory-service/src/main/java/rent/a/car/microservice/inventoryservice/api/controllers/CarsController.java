package rent.a.car.microservice.inventoryservice.api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rent.a.car.microservice.commonpackage.utils.dto.ClientResponse;
import rent.a.car.microservice.inventoryservice.business.abstracts.CarService;
import rent.a.car.microservice.inventoryservice.business.dto.requests.creates.CreateCarRequest;
import rent.a.car.microservice.inventoryservice.business.dto.requests.updates.UpdateCarRequest;
import rent.a.car.microservice.inventoryservice.business.dto.responses.creates.CreateCarResponse;
import rent.a.car.microservice.inventoryservice.business.dto.responses.gets.car.GetAllCarsResponse;
import rent.a.car.microservice.inventoryservice.business.dto.responses.gets.car.GetCarResponse;
import rent.a.car.microservice.inventoryservice.business.dto.responses.updates.UpdateCarResponse;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cars")
public class CarsController {
    private final CarService service;

    @GetMapping
    public List<GetAllCarsResponse> getAll()
    { return service.getAll(); }

    @GetMapping("/{id}")
    public GetCarResponse getById(@PathVariable UUID id)
    { return service.getById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCarResponse add(@Valid @RequestBody CreateCarRequest request)
    { return service.add(request); }

    @PutMapping("/{id}")
    public UpdateCarResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateCarRequest request)
    { return service.update(id, request); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id)
    { service.delete(id); }

    @GetMapping("/check-car-available/{id}")
    public ClientResponse checkIfCarAvailable(@PathVariable UUID id)
    { return service.checkIfCarAvailable(id); }
}