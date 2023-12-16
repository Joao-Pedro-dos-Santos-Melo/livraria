package com.example.livraria.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthorAlreadyExistException extends RuntimeException{

    public AuthorAlreadyExistException (String massege){super(massege);}
}
