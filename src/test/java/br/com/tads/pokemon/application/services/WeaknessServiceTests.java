package br.com.tads.pokemon.application.services;

import br.com.tads.pokemon.dtos.PokemonTypeDto;
import br.com.tads.pokemon.dtos.WeaknessDto;
import br.com.tads.pokemon.entities.Pokemon;
import br.com.tads.pokemon.entities.Type;
import br.com.tads.pokemon.entities.Weakness;
import br.com.tads.pokemon.exceptions.ConflictException;
import br.com.tads.pokemon.exceptions.NotFoundException;
import br.com.tads.pokemon.repositories.PokemonRepository;
import br.com.tads.pokemon.repositories.WeaknessRepository;
import br.com.tads.pokemon.services.WeaknessService;
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

public class WeaknessServiceTests {
    private Weakness weakness;
    private Type type;

    @InjectMocks
    private WeaknessService weaknessService;

    @Mock
    private WeaknessRepository weaknessRepository;

    @Mock
    private PokemonRepository pokemonRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        weakness = new Weakness();
        weakness.setId(1);
        weakness.setName("TestWeakness");

        type = new Type();
        type.setId(1);
        type.setName("TestWeakness");
    }

    @Test
    public void testCreateWeakness() {
        PokemonTypeDto weaknessDto = PokemonTypeDto.assignToDto(type);

        Mockito.when(weaknessRepository.findWeaknessByName("TestWeakness")).thenReturn(Optional.empty());
        Mockito.when(weaknessRepository.save(Mockito.any())).thenReturn(weakness);

        WeaknessDto result = weaknessService.create(weaknessDto);
        assertEquals("TestWeakness", result.getName());
    }

    @Test
    public void testCreateWeaknessConflict() {
        PokemonTypeDto weaknessDto = PokemonTypeDto.assignToDto(type);

        Mockito.when(weaknessRepository.findWeaknessByName("TESTWEAKNESS")).thenReturn(Optional.of(weakness));

        assertThrows(ConflictException.class, () -> weaknessService.create(weaknessDto));
    }

    @Test
    public void testDeleteWeakness() {
        Mockito.when(weaknessRepository.findById(1)).thenReturn(Optional.of(weakness));
        Mockito.when(pokemonRepository.findAllByWeaknessId(1)).thenReturn(Collections.emptyList());

        WeaknessDto result = weaknessService.deleteById(1);
        assertEquals("TestWeakness", result.getName());
    }

    @Test
    public void testDeleteWeaknessInUse() {
        Pokemon pokemon = new Pokemon();
        pokemon.setId(1);

        Mockito.when(weaknessRepository.findById(1)).thenReturn(Optional.of(weakness));
        Mockito.when(pokemonRepository.findAllByWeaknessId(1)).thenReturn(Collections.singletonList(new Pokemon()));

        assertThrows(ConflictException.class, () -> weaknessService.deleteById(1));
    }

    @Test
    public void testUpdateWeakness() {
        PokemonTypeDto weaknessDto = PokemonTypeDto.assignToDto(type);

        Mockito.when(weaknessRepository.findById(1)).thenReturn(Optional.of(weakness));
        Mockito.when(weaknessRepository.findWeaknessByName("TestWeakness")).thenReturn(Optional.empty());
        Mockito.when(weaknessRepository.save(Mockito.any())).thenReturn(weakness);

        WeaknessDto result = weaknessService.updateById(1, weaknessDto);
        assertEquals("TESTWEAKNESS", result.getName());
    }

    @Test
    public void testUpdateWeaknessConflict() {
        PokemonTypeDto weaknessDto = PokemonTypeDto.assignToDto(type);

        Mockito.when(weaknessRepository.findById(1)).thenReturn(Optional.of(weakness));
        Mockito.when(weaknessRepository.findWeaknessByName("TESTWEAKNESS")).thenReturn(Optional.of(weakness));

        assertThrows(ConflictException.class, () -> weaknessService.updateById(1, weaknessDto));
    }

    @Test
    public void testUpdateWeaknessNotFound() {
        PokemonTypeDto weaknessDto = PokemonTypeDto.assignToDto(type);

        Mockito.when(weaknessRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> weaknessService.updateById(1, weaknessDto));
    }
}
