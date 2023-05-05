package rent.a.car.microservice.inventoryservice.business.abstracts;

import rent.a.car.microservice.inventoryservice.business.dto.requests.creates.CreateModelRequest;
import rent.a.car.microservice.inventoryservice.business.dto.requests.updates.UpdateModelRequest;
import rent.a.car.microservice.inventoryservice.business.dto.responses.creates.CreateModelResponse;
import rent.a.car.microservice.inventoryservice.business.dto.responses.gets.model.GetAllModelsResponse;
import rent.a.car.microservice.inventoryservice.business.dto.responses.gets.model.GetModelResponse;
import rent.a.car.microservice.inventoryservice.business.dto.responses.updates.UpdateModelResponse;

import java.util.List;
import java.util.UUID;

public interface ModelService {
    List<GetAllModelsResponse> getAll();
    GetModelResponse getById(UUID id);
    CreateModelResponse add(CreateModelRequest request);
    UpdateModelResponse update(UUID id, UpdateModelRequest request);
    void delete(UUID id);
}
