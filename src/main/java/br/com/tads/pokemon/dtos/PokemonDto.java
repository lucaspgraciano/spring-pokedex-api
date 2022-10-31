package br.com.tads.pokemon.dtos;

import br.com.tads.pokemon.entities.Ability;
import br.com.tads.pokemon.entities.Pokemon;
import br.com.tads.pokemon.entities.Type;
import br.com.tads.pokemon.entities.Weakness;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PokemonDto implements Serializable {
    private static final long serialVersionUID = -6014710519590404715L;

    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private Long height;

    @NotNull
    private Long weight;

    @Min(1)
    @Max(100)
    @NotNull
    private Integer health;

    @Min(1)
    @Max(100)
    @NotNull
    private Integer attack;

    @Min(1)
    @Max(100)
    @NotNull
    private Integer defense;

    @Min(1)
    @Max(100)
    @NotNull
    private Integer specialAttack;

    @Min(1)
    @Max(100)
    @NotNull
    private Integer specialDefense;

    @Min(1)
    @Max(100)
    @NotNull
    private Integer speed;

    @NotEmpty
    private List<String> types;

    @NotEmpty
    private List<String> weaknesses;

    @NotEmpty
    private List<String> abilities;

    public static PokemonDto assignToDto(Pokemon entity) {
        PokemonDto dto = new PokemonDto();
        dto.id = entity.getId();
        dto.name = entity.getName();
        dto.height = entity.getHeight();
        dto.weight = entity.getWeight();
        dto.health = entity.getHealth();
        dto.attack = entity.getAttack();
        dto.defense = entity.getDefense();
        dto.specialAttack = entity.getSpecialAttack();
        dto.specialDefense = entity.getSpecialDefense();
        dto.speed = entity.getSpeed();
        dto.types = entity.getTypes().stream()
                .map(Type::getName)
                .collect(Collectors.toList());
        dto.weaknesses = entity.getWeaknesses().stream()
                .map(Weakness::getName)
                .collect(Collectors.toList());
        dto.abilities = entity.getAbilities().stream()
                .map(Ability::getName)
                .collect(Collectors.toList());
        return dto;
    }
}
