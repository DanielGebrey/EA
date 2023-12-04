package customers;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
@Bean
public CustomerService customerService(CustomerRepository customerRepository,EmailSender emailSender){
CustomerServiceImpl customerService = new CustomerServiceImpl();

customerService.setCustomerRepository(customerRepository);
customerService.setEmailSender(emailSender);
return customerService;
}
@Bean
public EmailSender emailSender(Logger logger){
return new EmailSenderImpl(logger);
}

@Bean
public CustomerRepository customerRepository(Logger logger){
return new CustomerRepositoryImpl(logger);
}

@Bean
public Logger logger(){
return new LoggerImpl();
}
}
