package br.com.tads.pokemon.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException{
    private static final long serialVersionUID = -3575799075034597896L;

    public ConflictException(String message, Object... args) { super(String.format(message, args)); }
}
