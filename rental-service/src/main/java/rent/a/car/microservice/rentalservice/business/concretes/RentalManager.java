package rent.a.car.microservice.rentalservice.business.concretes;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rent.a.car.microservice.commonpackage.events.rental.RentalCreatedEvent;
import rent.a.car.microservice.commonpackage.utils.mappers.ModelMapperService;
import rent.a.car.microservice.rentalservice.api.clients.CarClient;
import rent.a.car.microservice.rentalservice.business.abstracts.RentalService;
import rent.a.car.microservice.rentalservice.business.dto.requests.CreateRentalRequest;
import rent.a.car.microservice.rentalservice.business.dto.requests.UpdateRentalRequest;
import rent.a.car.microservice.rentalservice.business.dto.responses.CreateRentalResponse;
import rent.a.car.microservice.rentalservice.business.dto.responses.GetAllRentalsResponse;
import rent.a.car.microservice.rentalservice.business.dto.responses.GetRentalResponse;
import rent.a.car.microservice.rentalservice.business.dto.responses.UpdateRentalResponse;
import rent.a.car.microservice.rentalservice.business.kafka.producer.RentalProducer;
import rent.a.car.microservice.rentalservice.business.rules.RentalBusinessRules;
import rent.a.car.microservice.rentalservice.entities.Rental;
import rent.a.car.microservice.rentalservice.repository.RentalRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RentalManager implements RentalService {
    private final ModelMapperService mapper;
    private final RentalRepository repository;
    private final RentalBusinessRules rules;
    private final CarClient carClient;
    private final RentalProducer producer;

    @Override
    public List<GetAllRentalsResponse> getAll() {
        List<Rental> rentals = repository.findAll();

        List<GetAllRentalsResponse> responses = rentals
                .stream()
                .map(rental -> mapper.forResponse().map(rental, GetAllRentalsResponse.class))
                .toList();

        return responses;
    }

    @Override
    public GetRentalResponse getById(UUID id) {
        rules.checkIfRentalExists(id);

        Rental rental = repository.findById(id).orElseThrow();

        GetRentalResponse response = mapper.forResponse().map(rental, GetRentalResponse.class);

        return response;
    }

    @Override
    public CreateRentalResponse add(CreateRentalRequest request) {
        carClient.checkIfCarAvailable(request.getCarId());

        Rental rental = mapper.forRequest().map(request, Rental.class);

        rental.setId(UUID.randomUUID());
        rental.setTotalPrice(getTotalPrice(rental));
        rental.setRentedAt(LocalDate.now());
        repository.save(rental);

        sendKafkaRentalCreatedEvent(request.getCarId());

        CreateRentalResponse response = mapper.forResponse().map(rental, CreateRentalResponse.class);

        return response;
    }

    @Override
    public UpdateRentalResponse update(UUID id, UpdateRentalRequest request) {
        rules.checkIfRentalExists(id);

        Rental rental = mapper.forRequest().map(request, Rental.class);

        rental.setId(id);
        repository.save(rental);

        UpdateRentalResponse response = mapper.forResponse().map(rental, UpdateRentalResponse.class);

        return response;
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfRentalExists(id);

        repository.deleteById(id);
    }

    private double getTotalPrice(Rental rental) {
        return rental.getDailyPrice() * rental.getRentedForDays();
    }

    private void sendKafkaRentalCreatedEvent(UUID carId) {
        producer.sendMessage(new RentalCreatedEvent(carId));
    }
}