package rent.a.car.microservice.invoiceservice.business.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import rent.a.car.microservice.commonpackage.dto.PaymentCarResponse;
import rent.a.car.microservice.commonpackage.events.invoice.InvoiceCreatedEvent;
import rent.a.car.microservice.commonpackage.utils.mappers.ModelMapperService;
import rent.a.car.microservice.invoiceservice.business.abstracts.InvoiceService;
import rent.a.car.microservice.invoiceservice.entities.Invoice;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalConsumer {
    private final InvoiceService service;
    private final ModelMapperService mapper;


    @KafkaListener(
            topics = "invoice-created",
            groupId = "invoice-rental-create"
    )
    public void consume(PaymentCarResponse event) {
        Invoice invoice = mapper.forRequest().map(event, Invoice.class);
        service.add(invoice);
        log.info("Invoice created event consumed {}", event);
    }
}