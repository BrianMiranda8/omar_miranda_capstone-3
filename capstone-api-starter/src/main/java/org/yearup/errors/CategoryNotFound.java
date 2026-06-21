package org.yearup.errors;

public class CategoryNotFound extends RuntimeException{
    public CategoryNotFound(String msg){
        super(msg);
    }
}
