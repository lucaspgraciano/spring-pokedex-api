package br.com.tads.pokemon.services;

import br.com.tads.pokemon.dtos.PokemonDto;
import br.com.tads.pokemon.entities.Ability;
import br.com.tads.pokemon.entities.Pokemon;
import br.com.tads.pokemon.entities.Type;
import br.com.tads.pokemon.entities.Weakness;
import br.com.tads.pokemon.exceptions.ConflictException;
import br.com.tads.pokemon.exceptions.NotFoundException;
import br.com.tads.pokemon.repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.tads.pokemon.dtos.PokemonDto.assignToDto;
import static br.com.tads.pokemon.entities.Pokemon.assignToEntity;

@Service
public class PokemonService {
    private final PokemonRepository pokemonRepository;
    private final TypeRepository typeRepository;
    private final WeaknessRepository weaknessRepository;
    private final AbilityRepository abilityRepository;
    private final TrainerRepository trainerRepository;

    public PokemonService(PokemonRepository pokemonRepository,
                          TypeRepository typeRepository,
                          WeaknessRepository weaknessRepository,
                          AbilityRepository abilityRepository,
                          TrainerRepository trainerRepository) {
        this.pokemonRepository = pokemonRepository;
        this.typeRepository = typeRepository;
        this.weaknessRepository = weaknessRepository;
        this.abilityRepository = abilityRepository;
        this.trainerRepository = trainerRepository;
    }

    public List<PokemonDto> findAll() {
        return this.pokemonRepository.findAll().stream()
                .map(PokemonDto::assignToDto)
                .collect(Collectors.toList());
    }

    public PokemonDto create(PokemonDto dto) {
        List<Type> types = this.findTypesByNames(dto.getTypes());
        List<Weakness> weaknesses = this.findWeaknessesByNames(dto.getWeaknesses());
        List<Ability> abilities = this.findAbilitiesByNames(dto.getAbilities());
        this.doesPokemonAlreadyExists(dto.getName().toUpperCase());
        Pokemon pokemon = assignToEntity(new Pokemon(), dto, types, weaknesses, abilities);
        return assignToDto(this.pokemonRepository.save(pokemon));
    }

    public PokemonDto updateById(Integer id, PokemonDto dto) {
        Pokemon pokemonToBeUpdate = this.findPokemonById(id);
        List<Type> types = this.findTypesByNames(dto.getTypes());
        List<Weakness> weaknesses = this.findWeaknessesByNames(dto.getWeaknesses());
        List<Ability> abilities = this.findAbilitiesByNames(dto.getAbilities());
        if (!pokemonToBeUpdate.getName().equals(dto.getName().toUpperCase()))
            this.doesPokemonAlreadyExists(dto.getName().toUpperCase());
        Pokemon pokemon = assignToEntity(pokemonToBeUpdate, dto, types, weaknesses, abilities);
        return assignToDto(this.pokemonRepository.save(pokemon));
    }

    public PokemonDto deleteById(Integer id) {
        this.doesHaveTrainerWithPokemon(id);
        Pokemon pokemonToBeDelete = this.findPokemonById(id);
        PokemonDto dto = assignToDto(pokemonToBeDelete);
        this.pokemonRepository.delete(pokemonToBeDelete);
        return dto;
    }

    public PokemonDto findById(Integer id) {
        return assignToDto(this.findPokemonById(id));
    }

    public List<PokemonDto> findPokemonsByTypes(String type) {
        return this.pokemonRepository.findAllByTypeId(this.findTypeByName(type.toUpperCase()).getId()).stream()
                .map(PokemonDto::assignToDto)
                .collect(Collectors.toList());
    }

    public List<PokemonDto> findTenMostCatchedPokemons() {
        return this.pokemonRepository.findTenMostCatchedPokemons().stream()
                .map(PokemonDto::assignToDto)
                .collect(Collectors.toList());
    }

    private List<Type> findTypesByNames(List<String> typeNames) {
        return typeNames.stream()
                .map(name -> this.typeRepository.findTypeByName(name.toUpperCase())
                        .orElseThrow(() -> new NotFoundException("Tipo %s nao foi encontrado", name)))
                .collect(Collectors.toList());
    }

    private List<Weakness> findWeaknessesByNames(List<String> weaknessNames) {
        return weaknessNames.stream()
                .map(name -> this.weaknessRepository.findWeaknessByName(name.toUpperCase())
                        .orElseThrow(() -> new NotFoundException("Fraqueza %s nao foi encontrada", name)))
                .collect(Collectors.toList());
    }

    private Type findTypeByName(String name) {
        return this.typeRepository.findTypeByName(name)
                .orElseThrow(() -> new NotFoundException("Tipo %s nao foi encontrado", name));
    }

    private List<Ability> findAbilitiesByNames(List<String> abilityNames) {
        return abilityNames.stream()
                .map(name -> this.abilityRepository.findAbilityByName(name.toUpperCase())
                        .orElseThrow(() -> new NotFoundException("Habilidade %s nao foi encontrada", name)))
                .collect(Collectors.toList());
    }

    private void doesPokemonAlreadyExists(String pokemonName) {
        if (this.pokemonRepository.findPokemonByName(pokemonName.toUpperCase()).isPresent())
            throw new ConflictException("Pokemon %s ja cadastrado", pokemonName);
    }

    private Pokemon findPokemonById(Integer id) {
        return this.pokemonRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pokemon com id %s nao encontrado", id));
    }

    private void doesHaveTrainerWithPokemon(Integer pokemonId) {
        if (!this.trainerRepository.findAllByPokemonId(pokemonId).isEmpty())
            throw new ConflictException("Pokemon com id %s esta sendo utilizada por um treinador", pokemonId);
    }
}
