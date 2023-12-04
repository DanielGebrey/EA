package customers;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service

public class LoggerImpl implements Logger{

	public void log(String logstring) {
		System.out.println("Logging "+LocalDateTime.now()+" "+logstring);		
	}

}
