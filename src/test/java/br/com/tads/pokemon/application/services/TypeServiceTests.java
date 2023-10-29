package br.com.tads.pokemon.application.services;

import br.com.tads.pokemon.dtos.PokemonTypeDto;
import br.com.tads.pokemon.entities.Pokemon;
import br.com.tads.pokemon.entities.Type;
import br.com.tads.pokemon.exceptions.ConflictException;
import br.com.tads.pokemon.exceptions.NotFoundException;
import br.com.tads.pokemon.repositories.PokemonRepository;
import br.com.tads.pokemon.repositories.TypeRepository;
import br.com.tads.pokemon.services.TypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TypeServiceTests {
    private Type type;

    @InjectMocks
    private TypeService typeService;

    @Mock
    private TypeRepository typeRepository;

    @Mock
    private PokemonRepository pokemonRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        type = new Type();
        type.setId(1);
        type.setName("TestType");
    }

    @Test
    public void testCreateType() {
        PokemonTypeDto typeDto = PokemonTypeDto.assignToDto(type);

        Mockito.when(typeRepository.findTypeByName("TestType")).thenReturn(Optional.empty());
        Mockito.when(typeRepository.save(Mockito.any())).thenReturn(type);

        PokemonTypeDto result = typeService.create(typeDto);
        assertEquals("TestType", result.getName());
    }

    @Test
    public void testCreateTypeConflict() {
        PokemonTypeDto typeDto = PokemonTypeDto.assignToDto(type);

        Mockito.when(typeRepository.findTypeByName("TESTTYPE")).thenReturn(Optional.of(type));

        assertThrows(ConflictException.class, () -> typeService.create(typeDto));
    }

    @Test
    public void testDeleteType() {
        Mockito.when(typeRepository.findById(1)).thenReturn(Optional.of(type));
        Mockito.when(pokemonRepository.findAllByTypeId(1)).thenReturn(Collections.emptyList());

        PokemonTypeDto result = typeService.deleteById(1);
        assertEquals("TestType", result.getName());
    }

    @Test
    public void testDeleteTypeInUse() {
        Pokemon pokemon = new Pokemon();
        pokemon.setId(1);

        Mockito.when(typeRepository.findById(1)).thenReturn(Optional.of(type));
        Mockito.when(pokemonRepository.findAllByTypeId(1)).thenReturn(Collections.singletonList(pokemon));

        assertThrows(ConflictException.class, () -> typeService.deleteById(1));
    }

    @Test
    public void testUpdateType() {
        PokemonTypeDto typeDto = PokemonTypeDto.assignToDto(type);

        Mockito.when(typeRepository.findById(1)).thenReturn(Optional.of(type));
        Mockito.when(typeRepository.findTypeByName("TestType")).thenReturn(Optional.empty());
        Mockito.when(typeRepository.save(Mockito.any())).thenReturn(type);

        PokemonTypeDto result = typeService.updateById(1, typeDto);
        assertEquals("TESTTYPE", result.getName());
    }

    @Test
    public void testUpdateTypeConflict() {
        PokemonTypeDto typeDto = PokemonTypeDto.assignToDto(type);

        Mockito.when(typeRepository.findById(1)).thenReturn(Optional.of(type));
        Mockito.when(typeRepository.findTypeByName("TESTTYPE")).thenReturn(Optional.of(new Type()));

        assertThrows(ConflictException.class, () -> typeService.updateById(1, typeDto));
    }

    @Test
    public void testUpdateTypeNotFound() {
        PokemonTypeDto typeDto = new PokemonTypeDto();
        typeDto.setName("TestType");

        Mockito.when(typeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> typeService.updateById(1, typeDto));
    }
}
