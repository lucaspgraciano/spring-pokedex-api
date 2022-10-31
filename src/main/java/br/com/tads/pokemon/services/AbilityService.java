package br.com.tads.pokemon.services;

import br.com.tads.pokemon.dtos.AbilityDto;
import br.com.tads.pokemon.entities.Ability;
import br.com.tads.pokemon.exceptions.ConflictException;
import br.com.tads.pokemon.exceptions.NotFoundException;
import br.com.tads.pokemon.repositories.AbilityRepository;
import br.com.tads.pokemon.repositories.PokemonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.tads.pokemon.dtos.AbilityDto.assignToDto;
import static br.com.tads.pokemon.entities.Ability.assignToEntity;

@Service
public class AbilityService {
    private final AbilityRepository abilityRepository;
    private final PokemonRepository pokemonRepository;

    public AbilityService(AbilityRepository abilityRepository, PokemonRepository pokemonRepository) {
        this.abilityRepository = abilityRepository;
        this.pokemonRepository = pokemonRepository;
    }

    public List<AbilityDto> findAll() {
        return this.abilityRepository.findAll().stream()
                .map(AbilityDto::assignToDto)
                .collect(Collectors.toList());
    }

    public AbilityDto create(AbilityDto dto) {
        this.doesAbilityAlreadyExists(dto.getName().toUpperCase());
        Ability ability = assignToEntity(new Ability(), dto);
        return assignToDto(this.abilityRepository.save(ability));
    }

    public AbilityDto updateById(Integer id, AbilityDto abilityDto){
        Ability abilityToBeUpdate = this.findAbilityById(id);
        if (!abilityToBeUpdate.getName().equals(abilityDto.getName().toUpperCase()))
            this.doesAbilityAlreadyExists(abilityDto.getName().toUpperCase());
        Ability ability = assignToEntity(abilityToBeUpdate, abilityDto);
        return assignToDto(this.abilityRepository.save(ability));
    }

    public AbilityDto deleteById(Integer id) {
        this.doesHavePokemonWithAbility(id);
        Ability abilityToBeDelete = this.findAbilityById(id);
        AbilityDto dto = assignToDto(abilityToBeDelete);
        this.abilityRepository.delete(abilityToBeDelete);
        return dto;
    }

    private void doesAbilityAlreadyExists(String abilityName) {
        if (this.abilityRepository.findAbilityByName(abilityName).isPresent())
            throw new ConflictException("Habilidade %s ja cadastrada", abilityName);
    }

    private Ability findAbilityById(Integer id) {
        return this.abilityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Habilidade com id %s nao encontrada", id));
    }

    private void doesHavePokemonWithAbility(Integer abilityId) {
        if (!this.pokemonRepository.findAllByAbilityId(abilityId).isEmpty())
            throw new ConflictException("Habilidade com id %s esta sendo utilizada em um pokemon", abilityId);
    }
}
