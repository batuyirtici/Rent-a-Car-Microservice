package rent.a.car.microservice.filterservice.api.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rent.a.car.microservice.filterservice.business.abstracts.FilterService;
import rent.a.car.microservice.filterservice.business.dto.responses.GetAllFiltersResponse;
import rent.a.car.microservice.filterservice.business.dto.responses.GetFilterResponse;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/filters")
public class FiltersController {
    private final FilterService service;

    @GetMapping
    public List<GetAllFiltersResponse> getAll()
    { return service.getAll(); }

    @GetMapping("/{id}")
    public GetFilterResponse getById(@PathVariable String id)
    { return service.getById(id); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id)
    { service.delete(id); }
}