package customers;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

@Aspect
@Configuration
public class TraceAdvice {
    @Autowired
    Logger logger;

    @After("execution(* customers.EmailSender.sendEmail(..))")
    public void tracemethodA(JoinPoint joinpoint) {
        Object[] args = joinpoint.getArgs();
        String email = (String) args[0];
        String message = (String) args[1];
        EmailSenderImpl sender = (EmailSenderImpl)joinpoint.getTarget();
        logger.log("method =" + joinpoint.getSignature().getName() + " Email = " + email + " Message = " + message +"outgoing mail server"+sender.getOutgoingMailServer());
    }

        @Around("execution(* customers.CustomerRepository.*(..))")
        
        public Object profile (ProceedingJoinPoint call) throws Throwable{

        StopWatch clock = new StopWatch("");
        clock.start(call.toShortString());
        Object object= call.proceed();
        clock.stop();
        System.out.println(clock.prettyPrint());
        return object;
    }
}
