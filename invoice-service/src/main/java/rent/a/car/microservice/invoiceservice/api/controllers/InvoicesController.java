package rent.a.car.microservice.invoiceservice.api.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rent.a.car.microservice.invoiceservice.business.abstracts.InvoiceService;
import rent.a.car.microservice.invoiceservice.business.dto.responses.GetAllInvoicesResponse;
import rent.a.car.microservice.invoiceservice.business.dto.responses.GetInvoiceResponse;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/invoices")
public class InvoicesController {
    private final InvoiceService service;

    @GetMapping
    public List<GetAllInvoicesResponse> getAll()
    { return service.getAll(); }

    @GetMapping("/{id}")
    public GetInvoiceResponse getById(@PathVariable String id)
    { return service.getById(id); }
}