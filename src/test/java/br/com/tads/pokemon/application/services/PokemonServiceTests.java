package br.com.tads.pokemon.application.services;

import br.com.tads.pokemon.dtos.PokemonDto;
import br.com.tads.pokemon.entities.Pokemon;
import br.com.tads.pokemon.entities.Trainer;
import br.com.tads.pokemon.exceptions.ConflictException;
import br.com.tads.pokemon.exceptions.NotFoundException;
import br.com.tads.pokemon.repositories.*;
import br.com.tads.pokemon.services.PokemonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PokemonServiceTests {
    private Pokemon pokemon;

    @InjectMocks
    private PokemonService pokemonService;

    @Mock
    private PokemonRepository pokemonRepository;

    @Mock
    private TypeRepository typeRepository;

    @Mock
    private WeaknessRepository weaknessRepository;

    @Mock
    private AbilityRepository abilityRepository;

    @Mock
    private TrainerRepository trainerRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        pokemon = new Pokemon();
        pokemon.setId(1);
        pokemon.setName("TestPokemon");
        pokemon.setTypes(List.of());
        pokemon.setAbilities(List.of());
        pokemon.setWeaknesses(List.of());
        pokemon.setAbilities(List.of());
    }

    @Test
    public void testCreatePokemon() {
        PokemonDto pokemonDto = PokemonDto.assignToDto(pokemon);

        Mockito.when(pokemonRepository.findPokemonByName("TestPokemon")).thenReturn(Optional.empty());
        Mockito.when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);

        PokemonDto result = pokemonService.create(pokemonDto);
        assertEquals("TestPokemon", result.getName());
    }

    @Test
    public void testCreatePokemonConflict() {
        PokemonDto pokemonDto = PokemonDto.assignToDto(pokemon);

        Mockito.when(pokemonRepository.findPokemonByName("TESTPOKEMON")).thenReturn(Optional.of(pokemon));

        assertThrows(ConflictException.class, () -> pokemonService.create(pokemonDto));
    }

    @Test
    public void testDeletePokemon() {
        Mockito.when(pokemonRepository.findById(1)).thenReturn(Optional.of(pokemon));
        Mockito.when(trainerRepository.findAllByPokemonId(1)).thenReturn(Collections.emptyList());

        PokemonDto result = pokemonService.deleteById(1);
        assertEquals("TestPokemon", result.getName());
    }

    @Test
    public void testDeletePokemonInUse() {
        Trainer trainer = new Trainer();
        trainer.setId(1);

        Mockito.when(pokemonRepository.findById(1)).thenReturn(Optional.of(pokemon));
        Mockito.when(trainerRepository.findAllByPokemonId(1)).thenReturn(Collections.singletonList(trainer));

        assertThrows(ConflictException.class, () -> pokemonService.deleteById(1));
    }

    @Test
    public void testUpdatePokemon() {
        PokemonDto pokemonDto = PokemonDto.assignToDto(pokemon);

        Mockito.when(pokemonRepository.findById(1)).thenReturn(Optional.of(pokemon));
        Mockito.when(pokemonRepository.findPokemonByName("TestPokemon")).thenReturn(Optional.empty());
        Mockito.when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PokemonDto result = pokemonService.updateById(1, pokemonDto);
        assertEquals("TESTPOKEMON", result.getName());
    }

    @Test
    public void testUpdatePokemonConflict() {
        PokemonDto pokemonDto = PokemonDto.assignToDto(pokemon);

        Mockito.when(pokemonRepository.findById(1)).thenReturn(Optional.of(pokemon));
        Mockito.when(pokemonRepository.findPokemonByName("TESTPOKEMON")).thenReturn(Optional.of(pokemon));

        assertThrows(ConflictException.class, () -> pokemonService.updateById(1, pokemonDto));
    }

    @Test
    public void testUpdatePokemonNotFound() {
        PokemonDto pokemonDto = PokemonDto.assignToDto(pokemon);

        Mockito.when(pokemonRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> pokemonService.updateById(1, pokemonDto));
    }
}
