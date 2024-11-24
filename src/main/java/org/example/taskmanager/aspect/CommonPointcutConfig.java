package org.example.taskmanager.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcutConfig {

    @Pointcut("execution(* org.example.taskmanager.service.TaskServiceImpl.createTask(..))")
    public void getTimeLimitedMethods() {
    }

    @Pointcut("@annotation(org.example.taskmanager.annotations.HowManyRecords)")
    public void getEntityCount() {
    }

    @Pointcut("execution(* org.example.taskmanager.service.TaskServiceImpl.*(..))")
    public void getAllTaskServiceMethods() {
    }

}
