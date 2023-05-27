package rent.a.car.microservice.rentalservice.business.concretes;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rent.a.car.microservice.commonpackage.dto.CreateRentalPaymentRequest;
import rent.a.car.microservice.commonpackage.dto.GetCarResponse;
import rent.a.car.microservice.commonpackage.dto.PaymentCarResponse;
import rent.a.car.microservice.commonpackage.dto.PaymentRequest;
import rent.a.car.microservice.commonpackage.events.invoice.InvoiceCreatedEvent;
import rent.a.car.microservice.commonpackage.events.rental.RentalCreatedEvent;
import rent.a.car.microservice.commonpackage.events.rental.RentalDeletedEvent;
import rent.a.car.microservice.commonpackage.kafka.producer.KafkaProducer;
import rent.a.car.microservice.commonpackage.utils.mappers.ModelMapperService;
import rent.a.car.microservice.rentalservice.api.clients.car.CarClient;
import rent.a.car.microservice.rentalservice.business.abstracts.RentalService;
import rent.a.car.microservice.rentalservice.business.dto.requests.CreateRentalRequest;
import rent.a.car.microservice.rentalservice.business.dto.requests.UpdateRentalRequest;
import rent.a.car.microservice.rentalservice.business.dto.responses.CreateRentalResponse;
import rent.a.car.microservice.rentalservice.business.dto.responses.GetAllRentalsResponse;
import rent.a.car.microservice.rentalservice.business.dto.responses.GetRentalResponse;
import rent.a.car.microservice.rentalservice.business.dto.responses.UpdateRentalResponse;
import rent.a.car.microservice.rentalservice.business.rules.RentalBusinessRules;
import rent.a.car.microservice.rentalservice.entities.Rental;
import rent.a.car.microservice.rentalservice.repository.RentalRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RentalManager implements RentalService {
    private final RentalRepository repository;
    private final ModelMapperService mapper;
    private final RentalBusinessRules rules;
    private final KafkaProducer producer;
    private final CarClient carClient;

    @Override
    public List<GetAllRentalsResponse> getAll() {
        var rentals = repository.findAll();

        var response = rentals
                .stream()
                .map(rental -> mapper.forResponse().map(rental, GetAllRentalsResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetRentalResponse getById(UUID id) {
        rules.checkIfRentalExists(id);

        var rental = repository.findById(id).orElseThrow();

        var response = mapper.forResponse().map(rental, GetRentalResponse.class);

        return response;
    }

    @Override
    public CreateRentalResponse add(CreateRentalRequest request) {
        rules.ensureCarIsAvailable(request.getCarId());

        var rental = mapper.forRequest().map(request, Rental.class);

        rental.setId(null);
        rental.setTotalPrice(getTotalPrice(rental));
        rental.setRentedAt(LocalDate.now());

        // Payment Service Step Senkron
        CreateRentalPaymentRequest paymentRequest = new CreateRentalPaymentRequest();
        mapper.forRequest().map(request.getPaymentRequest(), paymentRequest);
        paymentRequest.setPrice(getTotalPrice(rental));
        rules.ensurePaymentIsValid(paymentRequest);

        repository.save(rental);

        // Rental Step
        sendKafkaRentalCreatedMessage(rental.getCarId());

        // Invoice Step
//        sendKafkaInvoiceCreatedMessage(rental, paymentRequest);
        PaymentCarResponse paymentCarResponse = new PaymentCarResponse();
        createInvoiceRequest(request, paymentCarResponse,rental);

        var response = mapper.forResponse().map(rental, CreateRentalResponse.class);

        return response;
    }



    @Override
    public UpdateRentalResponse update(UUID id, UpdateRentalRequest request) {
        rules.checkIfRentalExists(id);

        var rental = mapper.forRequest().map(request, Rental.class);

        rental.setId(id);
        repository.save(rental);

        var response = mapper.forResponse().map(rental, UpdateRentalResponse.class);

        return response;
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfRentalExists(id);
        sendKafkaRentalDeletedEvent(id);
        repository.deleteById(id);

    }

//  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

    private void sendKafkaRentalCreatedMessage(UUID carId)
    { producer.sendMessage(new RentalCreatedEvent(carId),"rental-created"); }

    private void sendKafkaRentalDeletedEvent(UUID id) {
        var carId = repository.findById(id).orElseThrow().getCarId();
        producer.sendMessage(new RentalDeletedEvent(carId), "rental-deleted");
    }

//    private void sendKafkaInvoiceCreatedMessage(Rental rental, PaymentRequest request){
//        PaymentCarResponse event =new PaymentCarResponse();
//        mapper.forRequest().map(event, Rental.class);
//        event.setCarId(rental.getCarId());
//        event.setDailyPrice(rental.getDailyPrice());
//        event.setRentedForDays(rental.getRentedForDays());
//        event.setTotalPrice(rental.getTotalPrice());
//        event.setRentedAt(rental.getRentedAt());
//        event.setCardHolder(request.getCardHolder());
//        producer.sendMessage(event, "invoice-created");
//    }

    private void createInvoiceRequest(CreateRentalRequest request, PaymentCarResponse paymentCarResponse, Rental rental) {
        GetCarResponse car = carClient.checkIfCarInRental(request.getCarId());

        // TODO: rentedAt, brandName and modelName is null!
        paymentCarResponse.setCarId(request.getCarId());
        paymentCarResponse.setRentedAt(LocalDate.now());
        paymentCarResponse.setModelName(car.getModelName());
        paymentCarResponse.setBrandName(car.getModelBrandName());
        paymentCarResponse.setDailyPrice(request.getDailyPrice());
        paymentCarResponse.setRentedForDays(request.getRentedForDays());
        paymentCarResponse.setCardHolder(request.getPaymentRequest().getCardHolder());
        paymentCarResponse.setPlate(car.getPlate());
        paymentCarResponse.setModelYear(car.getModelYear());

        producer.sendMessage(paymentCarResponse, "invoice-created");
    }

    private double getTotalPrice(Rental rental)
    { return rental.getDailyPrice()*rental.getRentedForDays(); }
}