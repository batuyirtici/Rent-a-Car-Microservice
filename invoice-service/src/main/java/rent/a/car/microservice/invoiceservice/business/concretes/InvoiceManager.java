package rent.a.car.microservice.invoiceservice.business.concretes;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rent.a.car.microservice.commonpackage.utils.mappers.ModelMapperService;
import rent.a.car.microservice.invoiceservice.business.abstracts.InvoiceService;
import rent.a.car.microservice.invoiceservice.business.dto.responses.GetAllInvoicesResponse;
import rent.a.car.microservice.invoiceservice.business.dto.responses.GetInvoiceResponse;
import rent.a.car.microservice.invoiceservice.entities.Invoice;
import rent.a.car.microservice.invoiceservice.repository.InvoiceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceManager implements InvoiceService {
    private final ModelMapperService mapper;
    private final InvoiceRepository repository;

    @Override
    public List<GetAllInvoicesResponse> getAll() {
        List<Invoice> invoices = repository.findAll();

        List<GetAllInvoicesResponse> responses = invoices
                .stream()
                .map(invoice -> mapper.forResponse().map(invoice, GetAllInvoicesResponse.class))
                .toList();

        return responses;
    }

    @Override
    public GetInvoiceResponse getById(String id) {
        Invoice invoice = repository.findById(id).orElseThrow();

        GetInvoiceResponse response = mapper.forResponse().map(invoice, GetInvoiceResponse.class);
        response.setRentedAt(invoice.getRentedAt());

        return response;
    }

    @Override
    public void add(Invoice invoice) { repository.save(invoice); }

    @Override
    public void delete(String id) { repository.deleteById(id); }
}