package org.example.taskmanager.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.example.taskmanager.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DataSource dataSource;

    @Before("org.example.taskmanager.aspect.CommonPointcutConfig.getAllTaskServiceMethods()")
    public void beforeAdvice(JoinPoint joinPoint) {
        try (Connection connection = dataSource.getConnection()) {
            if (connection == null || !connection.isValid(10)) {
                throw new RuntimeException("Database connection is not valid");
            }
            logger.info("Advice_Before method: {}", joinPoint.getSignature().toShortString());
            logger.info("Database connection is valid");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check database connection", e);
        }
    }

    @AfterReturning(pointcut = "org.example.taskmanager.aspect.CommonPointcutConfig.getEntityCount()", returning = "result")
    public void afterReturningAdvice(JoinPoint joinPoint, List<?> result) {
        logger.info("Advice_AfterReturning -- Count: {} Method {}", result.size(), joinPoint.getSignature().toShortString());
    }

    // TC
    // updateTask - RuntimeException
    // getTaskById - ResourceNotFoundException - correct behavior
    @AfterThrowing(
            pointcut = "org.example.taskmanager.aspect.CommonPointcutConfig.getAllTaskServiceMethods()",
            throwing = "exception")
    public void checkExistEntity(JoinPoint joinPoint, ResourceNotFoundException exception) {
        logger.error("Advice_AfterThrowing method: {}", joinPoint.getSignature().toShortString());
        logger.error("Exception: {}", exception.getMessage());
    }

    @Around("org.example.taskmanager.aspect.CommonPointcutConfig.getTimeLimitedMethods()")
    public Object logExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        logger.info("Advice_Around -- Start execution method: {}", pjp.getSignature().toShortString());
        try {
            Object result = pjp.proceed();
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            logger.info("Advice_Around -- Stop execution method: {}", pjp.getSignature() + " executed in " + executionTime + " ms");
            return result;
        } catch (Throwable e) {
            logger.error("Advice_Around method: {}", pjp.getSignature().toShortString());
            throw e;
        }
    }

}
