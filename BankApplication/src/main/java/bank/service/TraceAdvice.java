package bank.service;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

import bank.integration.logging.Logger;

@Aspect
@Configuration
public class TraceAdvice {
    @Autowired
    Logger logger;
@Before("execution (* bank.repository.*.*(..))")
 public void tracebeforemethod(JoinPoint joinpoint) {
logger.log("before execution of method "+joinpoint.getSignature().getName());
 }


    @Around("execution(* bank.service.*.*(..))")
 public Object profile (ProceedingJoinPoint call) throws Throwable{
 StopWatch clock = new StopWatch("");
 clock.start(call.toShortString());
 Object object= call.proceed();
 clock.stop();
 logger.log(clock.prettyPrint());
 return object;
 }

 @Before("execution (* bank.integration.jms.JMSSender.sendJMSMessage(..))")
 public void tracejmsmethod(JoinPoint joinpoint) {
    String text = (String)joinpoint.getArgs()[0];
logger.log("before execution of method "+joinpoint.getSignature().getName()+" the message = "+text);
 }
}