package br.com.tads.pokemon.services;

import br.com.tads.pokemon.dtos.TrainerDto;
import br.com.tads.pokemon.entities.Pokemon;
import br.com.tads.pokemon.entities.Trainer;
import br.com.tads.pokemon.exceptions.ConflictException;
import br.com.tads.pokemon.exceptions.NotFoundException;
import br.com.tads.pokemon.repositories.PokemonRepository;
import br.com.tads.pokemon.repositories.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.tads.pokemon.dtos.TrainerDto.assignToDto;
import static br.com.tads.pokemon.entities.Trainer.assignToEntity;

@Service
public class TrainerService {
    private final TrainerRepository trainerRepository;
    private final PokemonRepository pokemonRepository;

    public TrainerService(TrainerRepository trainerRepository,
                          PokemonRepository pokemonRepository) {
        this.trainerRepository = trainerRepository;
        this.pokemonRepository = pokemonRepository;
    }

    public List<TrainerDto> findAll() {
        return this.trainerRepository.findAll().stream()
                .map(TrainerDto::assignToDto)
                .collect(Collectors.toList());
    }

    public TrainerDto create(TrainerDto dto) {
        List<Pokemon> pokemons = this.findPokemonsByNames(dto.getPokemons());
        this.doesTrainerAlreadyExists(dto.getName().toUpperCase());
        Trainer trainer = assignToEntity(new Trainer(), dto, pokemons);
        return assignToDto(this.trainerRepository.save(trainer));
    }

    public TrainerDto updateById(Integer id, TrainerDto dto) {
        Trainer trainerToBeUpdate = this.findTrainerById(id);
        List<Pokemon> pokemons = this.findPokemonsByNames(dto.getPokemons());
        if (!trainerToBeUpdate.getName().equals(dto.getName().toUpperCase()))
            this.doesTrainerAlreadyExists(dto.getName().toUpperCase());
        Trainer trainer = assignToEntity(trainerToBeUpdate, dto, pokemons);
        return assignToDto(this.trainerRepository.save(trainer));
    }

    public TrainerDto deleteById(Integer id) {
        Trainer trainerToBeDelete = this.findTrainerById(id);
        TrainerDto dto = assignToDto(trainerToBeDelete);
        this.trainerRepository.delete(trainerToBeDelete);
        return dto;
    }

    private List<Pokemon> findPokemonsByNames(List<String> pokemonNames) {
        return pokemonNames.isEmpty()
                ? new ArrayList<>()
                : pokemonNames.stream()
                .map(name -> this.pokemonRepository.findPokemonByName(name.toUpperCase())
                        .orElseThrow(() -> new NotFoundException("Pokemon %s nao foi encontrado", name)))
                .collect(Collectors.toList());
    }

    private void doesTrainerAlreadyExists(String trainerName) {
        if (this.trainerRepository.findTrainerByName(trainerName).isPresent())
            throw new ConflictException("Treinador %s ja cadastrado", trainerName);
    }

    private Trainer findTrainerById(Integer id) {
        return this.trainerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Treinador com id %s nao encontrado", id));
    }
}
