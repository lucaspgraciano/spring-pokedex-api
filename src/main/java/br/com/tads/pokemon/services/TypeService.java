package br.com.tads.pokemon.services;

import br.com.tads.pokemon.dtos.PokemonTypeDto;
import br.com.tads.pokemon.entities.Type;
import br.com.tads.pokemon.exceptions.ConflictException;
import br.com.tads.pokemon.exceptions.NotFoundException;
import br.com.tads.pokemon.repositories.PokemonRepository;
import br.com.tads.pokemon.repositories.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.tads.pokemon.dtos.PokemonTypeDto.assignToDto;
import static br.com.tads.pokemon.entities.Type.assignToEntity;

@Service
public class TypeService {
    private final TypeRepository typeRepository;
    private final PokemonRepository pokemonRepository;

    public TypeService(TypeRepository typeRepository, PokemonRepository pokemonRepository) {
        this.typeRepository = typeRepository;
        this.pokemonRepository = pokemonRepository;
    }

    public List<PokemonTypeDto> findAll() {
        return this.typeRepository.findAll().stream()
                .map(PokemonTypeDto::assignToDto)
                .collect(Collectors.toList());
    }

    public PokemonTypeDto create(PokemonTypeDto dto) {
        this.doesTypeAlreadyExists(dto.getName().toUpperCase());
        Type type = assignToEntity(new Type(), dto);
        return assignToDto(this.typeRepository.save(type));
    }

    public PokemonTypeDto updateById(Integer id, PokemonTypeDto dto) {
        Type typeToBeUpdate = this.findTypeById(id);
        if (!typeToBeUpdate.getName().equals(dto.getName().toUpperCase()))
            this.doesTypeAlreadyExists(dto.getName().toUpperCase());
        Type type = assignToEntity(typeToBeUpdate, dto);
        return assignToDto(this.typeRepository.save(type));
    }

    public PokemonTypeDto deleteById(Integer id) {
        this.doesHavePokemonWithType(id);
        Type typeToBeDelete = this.findTypeById(id);
        PokemonTypeDto dto = assignToDto(typeToBeDelete);
        this.typeRepository.delete(typeToBeDelete);
        return dto;
    }

    private void doesTypeAlreadyExists(String typeName) {
        if (this.typeRepository.findTypeByName(typeName).isPresent())
                throw new ConflictException("Tipo %s ja cadastrada", typeName);

    }

    private Type findTypeById(Integer id) {
        return this.typeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tipo com id %s nao encontrado", id));
    }

    private void doesHavePokemonWithType(Integer typeId) {
        if (!this.pokemonRepository.findAllByTypeId(typeId).isEmpty())
            throw new ConflictException("Tipo com id %s esta sendo utilizado em um pokemon", typeId);
    }
}
