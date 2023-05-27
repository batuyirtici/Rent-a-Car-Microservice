package rent.a.car.microservice.invoiceservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import rent.a.car.microservice.invoiceservice.entities.Invoice;

public interface InvoiceRepository extends MongoRepository<Invoice, String> {}