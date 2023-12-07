package bank.domain;

import java.lang.annotation.Inherited;

import javax.annotation.processing.Generated;

@Entity
public class Customer {
	@Id
	@GeneratedValue
	private int id;
	
	private String name;

	public Customer(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
