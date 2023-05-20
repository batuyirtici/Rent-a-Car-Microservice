package rent.a.car.microservice.maintenanceservice.business.concretes;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rent.a.car.microservice.commonpackage.events.maintenance.MaintenanceCreatedEvent;
import rent.a.car.microservice.commonpackage.events.maintenance.MaintenanceDeletedEvent;
import rent.a.car.microservice.commonpackage.events.maintenance.MaintenanceReturnedEvent;
import rent.a.car.microservice.commonpackage.kafka.producer.KafkaProducer;
import rent.a.car.microservice.commonpackage.utils.mappers.ModelMapperService;
import rent.a.car.microservice.maintenanceservice.business.abstracts.MaintenanceService;
import rent.a.car.microservice.maintenanceservice.business.dto.requests.CreateMaintenanceRequest;
import rent.a.car.microservice.maintenanceservice.business.dto.requests.UpdateMaintenanceRequest;
import rent.a.car.microservice.maintenanceservice.business.dto.responses.CreateMaintenanceResponse;
import rent.a.car.microservice.maintenanceservice.business.dto.responses.GetAllMaintenancesResponse;
import rent.a.car.microservice.maintenanceservice.business.dto.responses.GetMaintenanceResponse;
import rent.a.car.microservice.maintenanceservice.business.dto.responses.UpdateMaintenanceResponse;
import rent.a.car.microservice.maintenanceservice.business.rules.MaintenanceBusinessRules;
import rent.a.car.microservice.maintenanceservice.entities.Maintenance;
import rent.a.car.microservice.maintenanceservice.repository.MaintenanceRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaintenanceManager implements MaintenanceService {

    private final MaintenanceRepository repository;
    private final ModelMapperService mapper;
    private final KafkaProducer producer;
    private final MaintenanceBusinessRules rules;


    @Override
    public List<GetAllMaintenancesResponse> getAll() {
        List<Maintenance> maintenances = repository.findAll();

        List<GetAllMaintenancesResponse> response = maintenances.stream()
                .map(maintenance -> mapper.forResponse().map(maintenance, GetAllMaintenancesResponse.class)).toList();

        return response;
    }

    @Override
    public GetMaintenanceResponse getById(UUID id) {
        rules.checkIfMaintenanceExists(id);

        Maintenance maintenance = repository.findById(id).orElseThrow();

        GetMaintenanceResponse response = mapper.forResponse().map(maintenance, GetMaintenanceResponse.class);

        return response;
    }

    @Override
    public GetMaintenanceResponse returnCarFromMaintenance(UUID carId) {
        rules.checkIfCarIsNotUnderMaintenance(carId);

        Maintenance maintenance = repository.findMaintenanceByCarIdAndIsCompletedFalse(carId);
        maintenance.setCompleted(true);
        maintenance.setEndDate(LocalDateTime.now());

        repository.save(maintenance);

        sendKafkaMaintenanceReturnEvent(maintenance.getId());

        GetMaintenanceResponse response = mapper.forResponse().map(maintenance, GetMaintenanceResponse.class);

        return response;
    }

    @Override
    public CreateMaintenanceResponse add(CreateMaintenanceRequest request) {
        rules.checkIfCarAvailable(request.getCarId());
        rules.checkIfCarUnderMaintenance(request.getCarId());

        Maintenance maintenance = mapper.forRequest().map(request, Maintenance.class);

        maintenance.setId(UUID.randomUUID());
        maintenance.setCompleted(false);
        maintenance.setStartDate(LocalDateTime.now());
        maintenance.setEndDate(null);

        repository.save(maintenance);

        sendKafkaMaintenanceCreatedEvent(maintenance.getCarId());

        CreateMaintenanceResponse response = mapper.forResponse().map(maintenance, CreateMaintenanceResponse.class);

        return response;
    }

    @Override
    public UpdateMaintenanceResponse update(UUID id, UpdateMaintenanceRequest request) {
        rules.checkIfMaintenanceExists(id);

        Maintenance maintenance = mapper.forRequest().map(request, Maintenance.class);

        maintenance.setId(id);
        repository.save(maintenance);

        UpdateMaintenanceResponse response = mapper.forResponse().map(maintenance, UpdateMaintenanceResponse.class);

        return response;
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfMaintenanceExists(id);

        sendKafkaMaintenanceDeletedEvent(id);

        repository.deleteById(id);

    }

//  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

    private void sendKafkaMaintenanceCreatedEvent(UUID carId)
    { producer.sendMessage(new MaintenanceCreatedEvent(carId), "maintenance-created"); }

    private void sendKafkaMaintenanceDeletedEvent(UUID id) {
        var carId = repository.findById(id).orElseThrow().getCarId();
        producer.sendMessage(new MaintenanceDeletedEvent(carId), "maintenance-deleted");
    }

    private void sendKafkaMaintenanceReturnEvent(UUID id) {
        var carId = repository.findById(id).orElseThrow().getCarId();
        producer.sendMessage(new MaintenanceReturnedEvent(carId), "maintenance-returned");
    }
}