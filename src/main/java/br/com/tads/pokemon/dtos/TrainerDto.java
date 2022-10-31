package br.com.tads.pokemon.dtos;

import br.com.tads.pokemon.entities.Pokemon;
import br.com.tads.pokemon.entities.Trainer;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TrainerDto implements Serializable {
    private static final long serialVersionUID = 3399606928985699022L;

    private Integer id;

    @NotBlank
    private String name;

    @Min(0)
    @NotNull
    private Long experience;

    @Min(0)
    @NotNull
    private Integer pokeballs;

    private List<String> pokemons;

    public static TrainerDto assignToDto(Trainer entity) {
        TrainerDto dto = new TrainerDto();
        dto.id = entity.getId();
        dto.name = entity.getName();
        dto.experience = entity.getExperience();
        dto.pokeballs = entity.getPokeballs();
        dto.pokemons = entity.getPokemons().stream()
                .map(Pokemon::getName)
                .collect(Collectors.toList());
        return dto;
    }
}
