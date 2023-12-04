package bank.integration.jms;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class JMSSenderImpl implements JMSSender{
	
	public void sendJMSMessage (String text){
		System.out.println("JMSSender: sending JMS message ="+text);
	}

}
