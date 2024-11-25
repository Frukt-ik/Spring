package ua.springApp.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PeopleControllerLoggingAspect {

    @Before("execution(public String peopleList(org.springframework.ui.Model))")
    public void logPeopleListAdvice(JoinPoint joinPoint) {
        System.out.println("Executing " + joinPoint.getSignature());
    }

    @Before("execution(public ua.springApp.models.Person getPerson(int))")
    public void logGetPersonAdvice(JoinPoint joinPoint) {
        System.out.println("Executing " + joinPoint.getSignature());
    }
}
