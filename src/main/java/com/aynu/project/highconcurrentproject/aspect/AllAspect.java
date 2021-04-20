package com.aynu.project.highconcurrentproject.aspect;



import com.aynu.project.highconcurrentproject.bean.Item;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import org.springframework.stereotype.Component;

@Component
@Aspect
public class AllAspect {

    @Pointcut("execution(* com.aynu.project.highconcurrentproject.service.ItemService.findById())")
    public void pointCut(){

    }

    /*@Before("pointCut()")
    public void  before(JoinPoint joinPoint, Item item){

        System.out.println("这是前置准备");

    }*/

    @Around("pointCut()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around before");
        joinPoint.proceed();
        System.out.println("around after");
    }

    /*@After("pointCut()")
    public void after(){
        System.out.println("这是后置准备");
    }*/

    @AfterReturning("pointCut()")
    public void afterReturning(){
        System.out.println("afterReturning");
    }

    @AfterThrowing("pointCut()")
    public void afterThrowing(){
        System.out.println("afterThrowing");
    }
}
