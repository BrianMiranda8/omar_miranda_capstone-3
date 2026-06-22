package org.yearup.errors;

public class ProductNotFound extends  RuntimeException{
    public ProductNotFound(String msg ){
        super(msg);
    }
}
