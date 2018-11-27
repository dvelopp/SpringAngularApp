package springAngularApp.system.ws;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import springAngularApp.system.ws.schema.ExceptionResponse;
import springAngularApp.system.ws.schema.ValidationErrorsResponse;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice(annotations = RestController.class)
public class BaseRestExceptionHandler {

    private static final String GENERAL_ERROR_MSG = "There is an error while processing the action. ";

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ExceptionResponse exception(Throwable exception) {
        return new ExceptionResponse(GENERAL_ERROR_MSG + exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public ValidationErrorsResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {

        Map<String, List<String>> fieldErrors = e.getBindingResult().getFieldErrors().stream()
                .collect(groupingBy(FieldError::getField, mapping(DefaultMessageSourceResolvable::getCode, toList())));

        List<String> globalErrors = e.getBindingResult().getGlobalErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(toList());

        return new ValidationErrorsResponse(fieldErrors, globalErrors);
    }

}
