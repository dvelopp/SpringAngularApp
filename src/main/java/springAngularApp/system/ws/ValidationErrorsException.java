package springAngularApp.system.ws;

import org.springframework.validation.Errors;

public class ValidationErrorsException extends RuntimeException {

    private Errors errors;

    public ValidationErrorsException(Errors errors) {
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
