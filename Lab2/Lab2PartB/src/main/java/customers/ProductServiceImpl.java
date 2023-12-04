package customers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

	// CustomerRepository customerRepository = new CustomerRepositoryImpl();

	// EmailSender emailSender = new EmailSenderImpl();


	ProductRepository productRepository;

	@Autowired

	public void setProductRepository(ProductRepository productRepository){
		this.productRepository = productRepository;
	}
	EmailSender emailSender;
	@Autowired
	public void setEmailSender(EmailSender emailSender){
		this.emailSender = emailSender;
	}


	public void addProduct(String name, String price) {
		Product product = new Product(name, price);
		productRepository.save(product);
		emailSender.sendEmail("productService@gmail.com", "Welcome " + name + " as a new customer");
	}
}