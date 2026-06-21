package org.yearup.errors;

public class DuplicateCategory extends  RuntimeException{
    public DuplicateCategory(String message){
        super(message);
    }
}
