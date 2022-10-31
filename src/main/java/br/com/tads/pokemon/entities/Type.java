package br.com.tads.pokemon.entities;

import br.com.tads.pokemon.dtos.PokemonTypeDto;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "types")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;

    @ManyToMany(mappedBy = "types", fetch = FetchType.LAZY)
    private List<Pokemon> types;

    public static Type assignToEntity(Type type, PokemonTypeDto dto) {
        type.name = dto.getName().toUpperCase();
        return type;
    }
}