package org.springboot.trendmartecommerceplatform.exceptionHandling;

public class ResourceNotFound extends RuntimeException{

    public ResourceNotFound(String message) {
        super(message);
    }
}
