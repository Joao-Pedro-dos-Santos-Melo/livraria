package com.example.livraria.controller.request;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorRequest implements Serializable {

    private String firstName;

    private String lastName;

    private String nationality;
}
