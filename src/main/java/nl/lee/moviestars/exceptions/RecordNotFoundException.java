package nl.lee.moviestars.exceptions;

import java.io.Serial;

public class RecordNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    public RecordNotFoundException() {
        super("Record not found.");
    }
}
