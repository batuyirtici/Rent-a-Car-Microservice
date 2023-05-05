package rent.a.car.microservice.inventoryservice.business.concretes;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rent.a.car.microservice.commonpackage.utils.mappers.ModelMapperService;
import rent.a.car.microservice.inventoryservice.business.abstracts.CarService;
import rent.a.car.microservice.inventoryservice.business.dto.requests.creates.CreateCarRequest;
import rent.a.car.microservice.inventoryservice.business.dto.requests.updates.UpdateCarRequest;
import rent.a.car.microservice.inventoryservice.business.dto.responses.creates.CreateCarResponse;
import rent.a.car.microservice.inventoryservice.business.dto.responses.gets.car.GetAllCarsResponse;
import rent.a.car.microservice.inventoryservice.business.dto.responses.gets.car.GetCarResponse;
import rent.a.car.microservice.inventoryservice.business.dto.responses.updates.UpdateCarResponse;
import rent.a.car.microservice.inventoryservice.business.rules.CarBusinessRules;
import rent.a.car.microservice.inventoryservice.entities.Car;
import rent.a.car.microservice.inventoryservice.entities.enums.State;
import rent.a.car.microservice.inventoryservice.repository.CarRepository;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CarManager implements CarService {
    private final CarBusinessRules rules;
    private final CarRepository repository;
    private final ModelMapperService mapper;

    @Override
    public List<GetAllCarsResponse> getAll() {
        var cars = repository.findAll();

        var response = cars
                .stream()
                .map(car -> mapper.forResponse().map(cars, GetAllCarsResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetCarResponse getById(UUID id) {
        var car = repository.findById(id).orElseThrow();

        var response = mapper.forResponse().map(car, GetCarResponse.class);

        return response;
    }

    @Override
    public CreateCarResponse add(CreateCarRequest request) {
        var car = mapper.forRequest().map(request, Car.class);

        car.setId(null);
        car.setState(State.Available);
        repository.save(car);

        var response = mapper.forResponse().map(car, CreateCarResponse.class);

        return response;
    }

    @Override
    public UpdateCarResponse update(UUID id, UpdateCarRequest request) {
        rules.checkIfCarExists(id);

        var car = mapper.forRequest().map(request, Car.class);
        car.setId(id);
        repository.save(car);

        var response = mapper.forResponse().map(car, UpdateCarResponse.class);

        return response;
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfCarExists(id);

        repository.deleteById(id);
    }
}
