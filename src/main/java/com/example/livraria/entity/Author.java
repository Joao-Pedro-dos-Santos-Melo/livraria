package com.example.livraria.entity;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author implements Serializable {

    private Integer id;

    private String firstName;

    private String lastName;

    private String nationality;

    private List<Livro> livros;

}
