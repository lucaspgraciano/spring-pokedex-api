package br.com.tads.pokemon.dtos;

import br.com.tads.pokemon.entities.Weakness;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
public class WeaknessDto implements Serializable {
    private static final long serialVersionUID = -5144542639479666655L;

    private Integer id;

    @NotEmpty
    private String name;

    public static WeaknessDto assignToDto(Weakness entity) {
        WeaknessDto dto = new WeaknessDto();
        dto.id = entity.getId();
        dto.name = entity.getName();
        return dto;
    }
}
