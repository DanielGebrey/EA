package customers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Profile("normal")
public class CustomerRepositoryImpl implements CustomerRepository{

	private Logger logger;
	@Autowired
	public CustomerRepositoryImpl(Logger logger){
		this.logger= logger;
	}

	public void save(Customer customer) {
		// simple sleep
		try {
			Thread.sleep(350);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("CustomerRepository Normal*******: saving customer "+customer.getName());
		logger.log("Customer is saved in the DB: "+ customer.getName() );
		
	}

}
