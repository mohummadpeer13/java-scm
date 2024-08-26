package com.example.demo.helpers;

public class ResourceNotFoundExeception extends RuntimeException{
    public ResourceNotFoundExeception(String message){
        super(message);
    }

    public ResourceNotFoundExeception(){
        super("Resource not found");
    }
}
