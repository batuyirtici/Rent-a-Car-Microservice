package rent.a.car.microservice.filterservice.business.abstracts;

import rent.a.car.microservice.filterservice.business.dto.responses.GetAllFiltersResponse;
import rent.a.car.microservice.filterservice.business.dto.responses.GetFilterResponse;
import rent.a.car.microservice.filterservice.entities.Filter;

import java.util.List;
import java.util.UUID;

public interface FilterService {
    List<GetAllFiltersResponse> getAll();
    GetFilterResponse getById(String id);
    void add(Filter filter);
    void delete(String id);
    void deleteByCarId(UUID carId);
    void deleteAllByBrandId(UUID brandId); // Bulk Delete
    void deleteAllByModelId(UUID modelId); // Bulk Delete
    Filter getByCarId(UUID carId);
}