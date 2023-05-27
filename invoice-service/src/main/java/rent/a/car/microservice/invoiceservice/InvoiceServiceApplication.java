package rent.a.car.microservice.invoiceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import rent.a.car.microservice.commonpackage.utils.constants.Paths;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {Paths.ConfigurationBasePackage, Paths.Invoice.ServiceBasePackage})
public class InvoiceServiceApplication
{ public static void main(String[] args) { SpringApplication.run(InvoiceServiceApplication.class, args); } }