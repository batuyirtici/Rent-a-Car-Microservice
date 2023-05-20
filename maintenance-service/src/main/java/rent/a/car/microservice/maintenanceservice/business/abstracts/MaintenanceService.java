package rent.a.car.microservice.maintenanceservice.business.abstracts;

import rent.a.car.microservice.maintenanceservice.business.dto.requests.CreateMaintenanceRequest;
import rent.a.car.microservice.maintenanceservice.business.dto.requests.UpdateMaintenanceRequest;
import rent.a.car.microservice.maintenanceservice.business.dto.responses.CreateMaintenanceResponse;
import rent.a.car.microservice.maintenanceservice.business.dto.responses.GetAllMaintenancesResponse;
import rent.a.car.microservice.maintenanceservice.business.dto.responses.GetMaintenanceResponse;
import rent.a.car.microservice.maintenanceservice.business.dto.responses.UpdateMaintenanceResponse;

import java.util.List;
import java.util.UUID;

public interface MaintenanceService {

    List<GetAllMaintenancesResponse> getAll();

    GetMaintenanceResponse getById(UUID id);

    GetMaintenanceResponse returnCarFromMaintenance(UUID carId);

    CreateMaintenanceResponse add(CreateMaintenanceRequest request);

    UpdateMaintenanceResponse update(UUID id, UpdateMaintenanceRequest request);

    void delete(UUID id);
}