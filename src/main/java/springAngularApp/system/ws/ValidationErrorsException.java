package springAngularApp.system.ws;

import org.springframework.validation.Errors;

import static org.springframework.util.Assert.notNull;

public final class ValidationErrorsException extends RuntimeException {

    private Errors errors;

    private ValidationErrorsException() {
    }

    public ValidationErrorsException(Errors errors) {
        notNull(errors);
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
