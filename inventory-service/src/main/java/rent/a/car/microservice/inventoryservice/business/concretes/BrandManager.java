package rent.a.car.microservice.inventoryservice.business.concretes;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rent.a.car.microservice.commonpackage.events.inventory.BrandDeletedEvent;
import rent.a.car.microservice.commonpackage.kafka.producer.KafkaProducer;
import rent.a.car.microservice.commonpackage.utils.mappers.ModelMapperService;
import rent.a.car.microservice.inventoryservice.business.abstracts.BrandService;
import rent.a.car.microservice.inventoryservice.business.dto.requests.creates.CreateBrandRequest;
import rent.a.car.microservice.inventoryservice.business.dto.requests.updates.UpdateBrandRequest;
import rent.a.car.microservice.inventoryservice.business.dto.responses.creates.CreateBrandResponse;
import rent.a.car.microservice.inventoryservice.business.dto.responses.gets.brand.GetAllBrandsResponse;
import rent.a.car.microservice.inventoryservice.business.dto.responses.gets.brand.GetBrandResponse;
import rent.a.car.microservice.inventoryservice.business.dto.responses.updates.UpdateBrandResponse;
import rent.a.car.microservice.inventoryservice.business.rules.BrandBusinessRules;
import rent.a.car.microservice.inventoryservice.entities.Brand;
import rent.a.car.microservice.inventoryservice.repository.BrandRepository;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BrandManager implements BrandService {
    private final BrandRepository repository;
    private final ModelMapperService mapper;
    private final BrandBusinessRules rules;
    private final KafkaProducer producer;

    @Override
    public List<GetAllBrandsResponse> getAll() {
        var brands = repository.findAll();

        var response = brands
                .stream()
                .map(brand -> mapper.forResponse().map(brand, GetAllBrandsResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetBrandResponse getById(UUID id) {
        rules.checkIfBrandExists(id);

        var brand = repository.findById(id).orElseThrow();
        var response = mapper.forResponse().map(brand, GetBrandResponse.class);

        return response;
    }

    @Override
    public CreateBrandResponse add(CreateBrandRequest request) {
        var brand = mapper.forRequest().map(request, Brand.class);

        repository.save(brand);

        var response = mapper.forResponse().map(brand, CreateBrandResponse.class);

        return response;
    }

    @Override
    public UpdateBrandResponse update(UUID id, UpdateBrandRequest request) {
        rules.checkIfBrandExists(id);

        var brand = mapper.forRequest().map(request, Brand.class);
        brand.setId(id);
        repository.save(brand);
        var response = mapper.forResponse().map(brand, UpdateBrandResponse.class);

        return response;
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfBrandExists(id);

        repository.deleteById(id);

        sendKafkaBrandDeletedEvent(id);
    }

//  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

    private void sendKafkaBrandDeletedEvent(UUID id)
    { producer.sendMessage(new BrandDeletedEvent(id), "brand-deleted"); }
}