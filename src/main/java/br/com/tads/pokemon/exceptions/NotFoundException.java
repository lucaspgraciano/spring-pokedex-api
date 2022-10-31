package br.com.tads.pokemon.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{
    private static final long serialVersionUID = -6171637583687091685L;

    public NotFoundException(String message, Object... args) { super(String.format(message, args)); }
}
