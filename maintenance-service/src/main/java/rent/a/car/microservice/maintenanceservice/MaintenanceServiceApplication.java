package rent.a.car.microservice.maintenanceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import rent.a.car.microservice.commonpackage.utils.constants.Paths;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {Paths.ConfigurationBasePackage, Paths.Maintenance.ServiceBasePackage})
public class MaintenanceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaintenanceServiceApplication.class, args);
	}

}
