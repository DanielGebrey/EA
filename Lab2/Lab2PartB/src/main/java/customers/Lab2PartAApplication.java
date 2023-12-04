package customers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Lab2PartAApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Lab2PartAApplication.class, args);

		CustomerService customerService = context.getBean("customerServiceImpl",
		CustomerService.class);

		ProductService productService = context.getBean("productServiceImpl",
		ProductService.class);

		customerService.addCustomer("Frank Brown", "fbrown@acme.com",
				"mainstreet 5", "Chicago", "60613");

			productService.addProduct("Apple", "50000");
	
	}

}
