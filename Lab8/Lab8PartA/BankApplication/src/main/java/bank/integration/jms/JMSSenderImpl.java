package bank.integration.jms;


public class JMSSenderImpl implements JMSSender{
	
	public void sendJMSMessage (String text){
		System.out.println("JMSSender: sending JMS message ="+text);
	}

}
