package rent.a.car.microservice.inventoryservice.business.abstracts;

import rent.a.car.microservice.inventoryservice.business.dto.requests.creates.CreateBrandRequest;
import rent.a.car.microservice.inventoryservice.business.dto.requests.updates.UpdateBrandRequest;
import rent.a.car.microservice.inventoryservice.business.dto.responses.creates.CreateBrandResponse;
import rent.a.car.microservice.inventoryservice.business.dto.responses.gets.brand.GetAllBrandsResponse;
import rent.a.car.microservice.inventoryservice.business.dto.responses.gets.brand.GetBrandResponse;
import rent.a.car.microservice.inventoryservice.business.dto.responses.updates.UpdateBrandResponse;

import java.util.List;
import java.util.UUID;

public interface BrandService {
    List<GetAllBrandsResponse> getAll();
    GetBrandResponse getById(UUID id);
    CreateBrandResponse add(CreateBrandRequest request);
    UpdateBrandResponse update(UUID id, UpdateBrandRequest request);
    void delete(UUID id);
}
