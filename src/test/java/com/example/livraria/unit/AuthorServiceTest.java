package com.example.livraria.unit;

import com.example.livraria.exception.AuthorNotFoundException;
import com.example.livraria.repository.AuthorRepository;
import com.example.livraria.service.AuthorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @InjectMocks
    private AuthorService service;

    @Mock
    private AuthorRepository repository;

    @Test
    @DisplayName("#findById > When the id os null > throw an exception")
    void findByWhenTheIdIsNullThrowAnException(){
        assertThrows(IllegalArgumentException.class, () -> service.findById(null));
    }

    @Test
    @DisplayName("#findById > When the id is not null > When no author is found > Return Throw an exception")
    void findByIdWhenIdIsNotNullWhenNoAuthorIsFoundThrowAnException(){
        when(repository.findById(2)).thenThrow(new AuthorNotFoundException());
        assertThrows(AuthorNotFoundException.class, () -> service.findById(2));
    }

}
