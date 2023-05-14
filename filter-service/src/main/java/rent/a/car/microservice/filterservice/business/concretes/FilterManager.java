package rent.a.car.microservice.filterservice.business.concretes;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rent.a.car.microservice.commonpackage.utils.mappers.ModelMapperService;
import rent.a.car.microservice.filterservice.business.abstracts.FilterService;
import rent.a.car.microservice.filterservice.business.dto.responses.GetAllFiltersResponse;
import rent.a.car.microservice.filterservice.business.dto.responses.GetFilterResponse;
import rent.a.car.microservice.filterservice.entities.Filter;
import rent.a.car.microservice.filterservice.repository.FilterRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FilterManager implements FilterService {
    private final FilterRepository repository;
    private final ModelMapperService mapper;

    @Override
    public List<GetAllFiltersResponse> getAll() {
        var filters = repository.findAll();

        var response = filters
                // TODO: brandId ve brandName null geliyor.
                .stream()
                .map(filter -> mapper.forResponse().map(filter, GetAllFiltersResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetFilterResponse getById(String id) {
        var filter = repository.findById(id).orElseThrow();

        var response = mapper.forResponse().map(filter, GetFilterResponse.class);

        return response;
    }

    @Override
    public void add(Filter filter) { repository.save(filter); }

    @Override
    public void delete(String id) { repository.deleteById(id); }

    @Override
    public void deleteByCarId(UUID carId) { repository.deleteByCarId(carId); }

    @Override
    public void deleteAllByBrandId(UUID brandId) { repository.deleteAllByBrandId(brandId); }

    @Override
    public void deleteAllByModelId(UUID modelId) {}

    @Override
    public Filter getByCarId(UUID carId) {return repository.findByCarId(carId); }
}