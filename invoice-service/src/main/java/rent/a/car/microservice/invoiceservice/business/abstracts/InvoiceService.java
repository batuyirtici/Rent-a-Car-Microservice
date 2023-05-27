package rent.a.car.microservice.invoiceservice.business.abstracts;


import rent.a.car.microservice.invoiceservice.business.dto.responses.GetAllInvoicesResponse;
import rent.a.car.microservice.invoiceservice.business.dto.responses.GetInvoiceResponse;
import rent.a.car.microservice.invoiceservice.entities.Invoice;

import java.util.List;

public interface InvoiceService {
    List<GetAllInvoicesResponse> getAll();
    GetInvoiceResponse getById(String id);
    void add(Invoice invoice);
    void delete(String id);
}