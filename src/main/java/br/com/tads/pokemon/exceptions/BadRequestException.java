package br.com.tads.pokemon.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{
    private static final long serialVersionUID = 7151661845043595351L;

    public BadRequestException(String message, Object... args) { super(String.format(message, args)); }
}
