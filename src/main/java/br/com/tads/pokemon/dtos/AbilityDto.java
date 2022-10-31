package br.com.tads.pokemon.dtos;

import br.com.tads.pokemon.entities.Ability;
import br.com.tads.pokemon.exceptions.BadRequestException;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
public class AbilityDto implements Serializable {
    private static final long serialVersionUID = 1126605669226889533L;

    private Integer id;

    @NotBlank
    private String name;

    @NotNull
    private Integer damage;

    public static AbilityDto assignToDto(Ability entity) {
        AbilityDto dto = new AbilityDto();
        dto.id = entity.getId();
        dto.name = entity.getName();
        dto.damage = entity.getDamage();
        return dto;
    }
}
