package br.com.tads.pokemon.application.services;

import br.com.tads.pokemon.dtos.AbilityDto;
import br.com.tads.pokemon.entities.Ability;
import br.com.tads.pokemon.entities.Pokemon;
import br.com.tads.pokemon.exceptions.ConflictException;
import br.com.tads.pokemon.exceptions.NotFoundException;
import br.com.tads.pokemon.repositories.AbilityRepository;
import br.com.tads.pokemon.repositories.PokemonRepository;
import br.com.tads.pokemon.services.AbilityService;
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

public class AbilityServiceTests {
    private Ability ability;

    @InjectMocks
    private AbilityService abilityService;

    @Mock
    private AbilityRepository abilityRepository;

    @Mock
    private PokemonRepository pokemonRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ability = new Ability();
        ability.setName("TestAbility");
        ability.setId(1);
    }

    @Test
    public void testCreateAbility() {
        AbilityDto abilityDto = AbilityDto.assignToDto(ability);
        Ability createdAbility = new Ability();
        createdAbility.setName("TestAbility");

        Mockito.when(abilityRepository.findAbilityByName("TestAbility")).thenReturn(Optional.empty());
        Mockito.when(abilityRepository.save(Mockito.any(Ability.class))).thenReturn(createdAbility);

        AbilityDto result = abilityService.create(abilityDto);
        assertEquals("TestAbility", result.getName());
    }

    @Test
    public void testCreateAbilityConflict() {
        AbilityDto abilityDto = AbilityDto.assignToDto(ability);

        Mockito.when(abilityRepository.findAbilityByName("TESTABILITY")).thenReturn(Optional.of(ability));

        assertThrows(ConflictException.class, () -> abilityService.create(abilityDto));
    }

    @Test
    public void testDeleteAbility() {
        Ability ability = new Ability();
        ability.setId(1);
        ability.setName("TestAbility");

        Mockito.when(abilityRepository.findById(1)).thenReturn(Optional.of(ability));
        Mockito.when(pokemonRepository.findAllByAbilityId(1)).thenReturn(Collections.emptyList());

        AbilityDto result = abilityService.deleteById(1);
        assertEquals("TestAbility", result.getName());
    }

    @Test
    public void testDeleteAbilityInUse() {
        Pokemon pokemon = new Pokemon();
        pokemon.setId(1);

        Mockito.when(abilityRepository.findById(1)).thenReturn(Optional.of(ability));
        Mockito.when(pokemonRepository.findAllByAbilityId(1)).thenReturn(Collections.singletonList(pokemon));

        assertThrows(ConflictException.class, () -> abilityService.deleteById(1));
    }

    @Test
    public void testUpdateAbility() {
        AbilityDto abilityDto = AbilityDto.assignToDto(ability);
        Ability existingAbility = new Ability();
        existingAbility.setId(1);
        existingAbility.setName("ExistingAbility");

        Mockito.when(abilityRepository.findById(1)).thenReturn(Optional.of(existingAbility));
        Mockito.when(abilityRepository.findAbilityByName("TestAbility")).thenReturn(Optional.empty());
        Mockito.when(abilityRepository.save(Mockito.any(Ability.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AbilityDto result = abilityService.updateById(1, abilityDto);
        assertEquals("TESTABILITY", result.getName());
    }

    @Test
    public void testUpdateAbilityConflict() {
        AbilityDto abilityDto = AbilityDto.assignToDto(ability);
        Ability existingAbility = new Ability();
        existingAbility.setId(1);
        existingAbility.setName("ExistingAbility");

        Mockito.when(abilityRepository.findById(1)).thenReturn(Optional.of(existingAbility));
        Mockito.when(abilityRepository.findAbilityByName("TESTABILITY")).thenReturn(Optional.of(ability));

        assertThrows(ConflictException.class, () -> abilityService.updateById(1, abilityDto));
    }

    @Test
    public void testUpdateAbilityNotFound() {
        AbilityDto abilityDto = AbilityDto.assignToDto(ability);

        Mockito.when(abilityRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> abilityService.updateById(1, abilityDto));
    }
}
