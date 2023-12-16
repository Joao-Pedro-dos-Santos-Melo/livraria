package com.example.livraria.controller.request;

import com.example.livraria.entity.Author;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest implements Serializable {

    private String title;

    private Integer publicationYear;

    private Integer pages;

    private Integer idAuthor;
}
