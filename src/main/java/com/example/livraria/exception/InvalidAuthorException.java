package com.example.livraria.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidAuthorException extends IllegalArgumentException{

    public InvalidAuthorException(String message){super(message);}
}
