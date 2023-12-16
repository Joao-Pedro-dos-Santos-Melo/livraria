package com.example.livraria.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException(String massege){super(massege);}
}
