/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blaze.actor;

import com.blaze.model.Product;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
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

    public void execute(Object objectMessage)  {
        try {
            log.info(abstractActorClass.toString());
            AbstractActor actor = abstractActorClass.newInstance();

            Callable<String> task = () -> {
                actor.receiveMessage(objectMessage);
                return "finish";
            };
            
            
            ExecutorService executor = Executors.newFixedThreadPool(poolNumber);
            Future<?> future = executor.submit(task);
            
//            while(!future.isDone()){
//                System.out.println("not done");
//                
//            }
            Object result = future.get(5,TimeUnit.SECONDS);
            System.out.println("done: "+result);
                executor.shutdown();
                executor.awaitTermination(5, TimeUnit.SECONDS);
            
        } catch (TimeoutException|ExecutionException|InterruptedException|InstantiationException | IllegalAccessException ex) {
            log.error(ex);
        }
    }
    
    public static void main(String[] args) {
        ActorRef actorRef = new ActorRef(FriesActor.class);
        actorRef.execute(new Product());
    }

}
