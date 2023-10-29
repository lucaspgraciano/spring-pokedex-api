package br.com.tads.pokemon.application.services;

import br.com.tads.pokemon.dtos.TrainerDto;
import br.com.tads.pokemon.entities.Trainer;
import br.com.tads.pokemon.exceptions.ConflictException;
import br.com.tads.pokemon.exceptions.NotFoundException;
import br.com.tads.pokemon.repositories.PokemonRepository;
import br.com.tads.pokemon.repositories.TrainerRepository;
import br.com.tads.pokemon.services.TrainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

public class TrainerServiceTests {
    private Trainer trainer;

    @InjectMocks
    private TrainerService trainerService;

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private PokemonRepository pokemonRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        trainer = new Trainer();
        trainer.setId(1);
        trainer.setName("TestTrainer");
        trainer.setPokemons(List.of());
    }

    @Test
    public void testCreateTrainer() {
        TrainerDto trainerDto = TrainerDto.assignToDto(trainer);

        Mockito.when(trainerRepository.findTrainerByName("TestTrainer")).thenReturn(Optional.empty());
        Mockito.when(trainerRepository.save(any(Trainer.class))).thenReturn(trainer);

        TrainerDto result = trainerService.create(trainerDto);
        assertEquals("TestTrainer", result.getName());
    }

    @Test
    public void testCreateTrainerConflict() {
        TrainerDto trainerDto = TrainerDto.assignToDto(trainer);

        Mockito.when(trainerRepository.findTrainerByName("TESTTRAINER")).thenReturn(Optional.of(trainer));
        assertThrows(ConflictException.class, () -> trainerService.create(trainerDto));
    }

    @Test
    public void testDeleteTrainer() {
        TrainerDto trainerDto = TrainerDto.assignToDto(trainer);

        Mockito.when(trainerRepository.findById(1)).thenReturn(Optional.of(trainer));

        TrainerDto result = trainerService.deleteById(1);
        assertEquals("TestTrainer", result.getName());
    }

    @Test
    public void testDeleteTrainerNotFound() {
        Mockito.when(trainerRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> trainerService.deleteById(1));
    }

    @Test
    public void testUpdateTrainer() {
        TrainerDto trainerDto = TrainerDto.assignToDto(trainer);

        Mockito.when(trainerRepository.findById(1)).thenReturn(Optional.of(trainer));
        Mockito.when(trainerRepository.findTrainerByName("TestTrainer")).thenReturn(Optional.empty());
        Mockito.when(trainerRepository.save(trainer)).thenReturn(trainer);
        TrainerDto result = trainerService.updateById(1, trainerDto);
        assertEquals("TESTTRAINER", result.getName());
    }

    @Test
    public void testUpdateTrainerConflict() {
        TrainerDto trainerDto = TrainerDto.assignToDto(trainer);

        Mockito.when(trainerRepository.findById(1)).thenReturn(Optional.of(trainer));
        Mockito.when(trainerRepository.findTrainerByName("TESTTRAINER")).thenReturn(Optional.of(trainer));
        assertThrows(ConflictException.class, () -> trainerService.updateById(1, trainerDto));
    }

    @Test
    public void testUpdateTrainerNotFound() {
        TrainerDto trainerDto = TrainerDto.assignToDto(trainer);

        Mockito.when(trainerRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> trainerService.updateById(1, trainerDto));
    }
}
