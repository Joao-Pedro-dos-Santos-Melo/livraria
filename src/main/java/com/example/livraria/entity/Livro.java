package com.example.livraria.entity;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Livro implements Serializable {

    private Integer id;

    private String title;

    private Integer publicationYear;

    private Integer pages;

    private Author author;
}
