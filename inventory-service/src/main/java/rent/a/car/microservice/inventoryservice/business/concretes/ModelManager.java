package rent.a.car.microservice.inventoryservice.business.concretes;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rent.a.car.microservice.commonpackage.utils.mappers.ModelMapperService;
import rent.a.car.microservice.inventoryservice.business.abstracts.ModelService;
import rent.a.car.microservice.inventoryservice.business.dto.requests.creates.CreateModelRequest;
import rent.a.car.microservice.inventoryservice.business.dto.requests.updates.UpdateModelRequest;
import rent.a.car.microservice.inventoryservice.business.dto.responses.creates.CreateModelResponse;
import rent.a.car.microservice.inventoryservice.business.dto.responses.gets.model.GetAllModelsResponse;
import rent.a.car.microservice.inventoryservice.business.dto.responses.gets.model.GetModelResponse;
import rent.a.car.microservice.inventoryservice.business.dto.responses.updates.UpdateModelResponse;
import rent.a.car.microservice.inventoryservice.business.rules.ModelBusinessRules;
import rent.a.car.microservice.inventoryservice.entities.Model;
import rent.a.car.microservice.inventoryservice.repository.ModelRepository;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ModelManager implements ModelService {
    private final ModelRepository repository;
    private final ModelMapperService mapper;
    private final ModelBusinessRules rules;

    @Override
    public List<GetAllModelsResponse> getAll() {
        var models = repository.findAll();

        var response = models
                .stream()
                .map(model -> mapper.forResponse().map(model, GetAllModelsResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetModelResponse getById(UUID id) {
        rules.checkIfModelExists(id);

        var model = repository.findById(id).orElseThrow();

        var response = mapper.forResponse().map(model, GetModelResponse.class);

        return response;
    }

    @Override
    public CreateModelResponse add(CreateModelRequest request) {
        var model = mapper.forRequest().map(request, Model.class);
        model.setId(UUID.randomUUID());
        repository.save(model);

        var response = mapper.forResponse().map(model, CreateModelResponse.class);

        return response;
    }

    @Override
    public UpdateModelResponse update(UUID id, UpdateModelRequest request) {
        rules.checkIfModelExists(id);

        var model = mapper.forRequest().map(request, Model.class);
        model.setId(id);
        repository.save(model);

        var response = mapper.forResponse().map(model, UpdateModelResponse.class);

        return response;
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfModelExists(id);

        repository.deleteById(id);
    }
}