package rent.a.car.microservice.inventoryservice.business.abstracts;

import rent.a.car.microservice.commonpackage.utils.dto.ClientResponse;
import rent.a.car.microservice.inventoryservice.business.dto.requests.creates.CreateCarRequest;
import rent.a.car.microservice.inventoryservice.business.dto.requests.updates.UpdateCarRequest;
import rent.a.car.microservice.inventoryservice.business.dto.responses.creates.CreateCarResponse;
import rent.a.car.microservice.inventoryservice.business.dto.responses.gets.car.GetAllCarsResponse;
import rent.a.car.microservice.inventoryservice.business.dto.responses.gets.car.GetCarResponse;
import rent.a.car.microservice.inventoryservice.business.dto.responses.updates.UpdateCarResponse;
import rent.a.car.microservice.inventoryservice.entities.enums.State;

import java.util.List;
import java.util.UUID;

public interface CarService {
    List<GetAllCarsResponse> getAll();
    GetCarResponse getById(UUID id);
    CreateCarResponse add(CreateCarRequest request);
    UpdateCarResponse update(UUID id, UpdateCarRequest request);
    void delete(UUID id);
    ClientResponse checkIfCarAvailable(UUID id);
    void changeStateByCarId(State state, UUID id);
}
