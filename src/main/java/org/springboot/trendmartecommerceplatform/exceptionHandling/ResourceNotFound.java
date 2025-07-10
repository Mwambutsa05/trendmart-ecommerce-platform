package org.springboot.trendmartecommerceplatform.exceptionHandling;

import lombok.Getter;

@Getter
public class ResourceNotFound extends RuntimeException{
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public ResourceNotFound(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        String.format("%s not found with %s: %s", resourceName, fieldName, fieldValue);

    }


    public ResourceNotFound(String message) {
        super(message);
    }
}
