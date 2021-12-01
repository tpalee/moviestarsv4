package nl.lee.moviestars.exceptions;

import java.io.Serial;

public class MovieAlreadyExistsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public MovieAlreadyExistsException() {
        super("Movie already exists.");
    }

}
