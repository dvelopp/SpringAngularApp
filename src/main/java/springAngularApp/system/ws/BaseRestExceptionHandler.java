package springAngularApp.system.ws;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import springAngularApp.system.ws.schema.ExceptionResponse;
import springAngularApp.system.ws.schema.ValidationErrorsResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice(annotations = RestController.class)
public class BaseRestExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ExceptionResponse exception(Throwable exception) {
        return new ExceptionResponse("There is an error while processing the action. " + exception.getMessage());
    }

    @ExceptionHandler(ValidationErrorsException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public ValidationErrorsResponse exception(ValidationErrorsException exception) {

        Map<String, List<String>> fieldErrors = exception.getErrors().getFieldErrors().stream()
                .collect(groupingBy(FieldError::getField, Collectors.mapping(DefaultMessageSourceResolvable::getCode, toList())));

        List<String> globalErrors = exception.getErrors().getGlobalErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(toList());

        return new ValidationErrorsResponse(fieldErrors, globalErrors);
    }
}
