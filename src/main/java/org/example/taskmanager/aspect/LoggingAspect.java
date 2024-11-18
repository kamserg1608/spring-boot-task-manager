package org.example.taskmanager.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.taskmanager.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;


@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("org.example.taskmanager.aspect.CommonPointcutConfig.getAllTaskServiceMethods()")
    public void beforeAdvice(JoinPoint joinPoint) {
        logger.info("Advice_Before method: {}", joinPoint.getSignature().toShortString());
    }

    @AfterReturning(pointcut = "org.example.taskmanager.aspect.CommonPointcutConfig.getEntityCount()", returning = "result")
    public void afterReturningAdvice(JoinPoint joinPoint, List<?> result) {
        logger.info("Advice_AfterReturning -- Count: {} Method {}", result.size(), joinPoint.getSignature().toShortString());
    }

    @AfterThrowing(
            pointcut = "org.example.taskmanager.aspect.CommonPointcutConfig.getAllTaskServiceMethods()",
            throwing = "exception")
    public void checkExistEntity(JoinPoint joinPoint, ResourceNotFoundException exception) {
        logger.error("Advice_AfterThrowing method: {}", joinPoint.getSignature().toShortString());
        logger.error("Exception: {}", exception.getMessage());
    }

    @Around("org.example.taskmanager.aspect.CommonPointcutConfig.getTimeLimitedMethods()")
    public Object logExecutionTime(ProceedingJoinPoint pjp) {
        long startTime = System.currentTimeMillis();
        Object result = null;
        logger.info("Advice_Around -- Start execution method: {}", pjp.getSignature().toShortString());
        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            logger.error("Advice_Around method: {}", pjp.getSignature().toShortString());
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        logger.info("Advice_Around -- Stop execution method: {}", pjp.getSignature() + " executed in " + executionTime + " ms");

        return result;
    }

}
