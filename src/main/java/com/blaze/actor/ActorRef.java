/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blaze.actor;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;


/**
 *
 * @author Jorge
 */
public class ActorRef {

    private Logger log = Logger.getLogger(getClass());

    private Class<? extends AbstractActor> abstractActorClass;
    private Integer poolNumber = 1;

    protected ActorRef(Class<? extends AbstractActor> abstractACtorClass) {
        this.abstractActorClass = abstractACtorClass;
    }

    protected ActorRef(Class<? extends AbstractActor> abstractACtorClass, Integer poolNumber) {
        this.abstractActorClass = abstractACtorClass;
        this.poolNumber = poolNumber;
    }

    public Class<? extends AbstractActor> getAbstractActorClass() {
        return this.abstractActorClass;
    }

    public void ask(Object objectMessage)  {
        try {
            log.info(abstractActorClass.toString());
//            AbstractActor actor = abstractActorClass.newInstance();
            AbstractActor actor = abstractActorClass.getConstructor().newInstance();

            Runnable task = () -> {
                actor.receiveMessage(objectMessage);
            };
            
            ExecutorService executor = Executors.newFixedThreadPool(poolNumber);
            Future<?> future = executor.submit(task);
            
//            Object result = future.get(10,TimeUnit.SECONDS);
            System.out.println("done: ");
                executor.shutdown();
                executor.awaitTermination(5, TimeUnit.SECONDS);
            
//        } catch (InvocationTargetException|NoSuchMethodException|TimeoutException|ExecutionException|InterruptedException|InstantiationException | IllegalAccessException ex) {
        } catch (InvocationTargetException|NoSuchMethodException|InterruptedException|InstantiationException | IllegalAccessException ex) {
            log.error(ex);
        }
    }
    
}
