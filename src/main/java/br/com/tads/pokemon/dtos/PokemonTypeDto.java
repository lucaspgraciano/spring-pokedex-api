package br.com.tads.pokemon.dtos;

import br.com.tads.pokemon.entities.Type;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class PokemonTypeDto implements Serializable {
    private static final long serialVersionUID = 6426190627904757283L;

    private Integer id;

    @NotEmpty
    private String name;

    public static PokemonTypeDto assignToDto(Type entity) {
        PokemonTypeDto typeDto = new PokemonTypeDto();
        typeDto.id = entity.getId();
        typeDto.name = entity.getName();
        return typeDto;
    }
}
