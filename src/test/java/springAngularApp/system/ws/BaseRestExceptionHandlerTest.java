package springAngularApp.system.ws;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import springAngularApp.system.ws.schema.ValidationErrorsResponse;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaseRestExceptionHandlerTest {

    @InjectMocks private BaseRestExceptionHandler testee;

    @Mock private BindingResult bindingResult;
    @Mock private MethodParameter methodParameter;

    private static final String TEST_OBJECT_NAME = "testObjectName";
    private static final String TEST_FIELD = "testField";
    private static final String TEST_FIELD_ERROR_MESSAGE = "testFieldErrorMessage";

    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Before
    public void setup() {
        when(bindingResult.getFieldErrors()).thenReturn(emptyList());
        when(bindingResult.getGlobalErrors()).thenReturn(emptyList());
        methodArgumentNotValidException = new MethodArgumentNotValidException(methodParameter, bindingResult);
    }

    @Test
    public void methodArgumentNotValidException_FieldError_FieldErrorHasBeenAdded() {
        addFieldError();

        ValidationErrorsResponse response = testee.methodArgumentNotValidException(methodArgumentNotValidException);

        assertThat(response.getFieldErrors()).hasSize(1);
        assertThat(response.getFieldErrors().get(TEST_FIELD)).contains(TEST_FIELD_ERROR_MESSAGE);
    }

    @Test
    public void methodArgumentNotValidException_NoFieldError_FieldErrorHasNotBeenAdded() {
        ValidationErrorsResponse response = testee.methodArgumentNotValidException(methodArgumentNotValidException);

        assertThat(response.getFieldErrors()).hasSize(0);
    }

    @Test
    public void methodArgumentNotValidException_GlobalError_GlobalErrorHasBeenAdded() {
        addGlobalError();

        ValidationErrorsResponse response = testee.methodArgumentNotValidException(methodArgumentNotValidException);

        assertThat(response.getGlobalErrors()).hasSize(1);
        assertThat(response.getGlobalErrors().get(0)).isEqualTo(TEST_FIELD_ERROR_MESSAGE);
    }

    @Test
    public void methodArgumentNotValidException_NoGlobalError_GlobalErrorHasNotBeenAdded() {
        ValidationErrorsResponse response = testee.methodArgumentNotValidException(methodArgumentNotValidException);

        assertThat(response.getGlobalErrors()).hasSize(0);
    }

    @Test
    public void methodArgumentNotValidException_FieldAndGlobalError_BothErrorsHaveBeenAdded() {
        addGlobalError();
        addFieldError();

        ValidationErrorsResponse response = testee.methodArgumentNotValidException(methodArgumentNotValidException);

        assertThat(response.getGlobalErrors()).hasSize(1);
        assertThat(response.getFieldErrors()).hasSize(1);
    }

    private void addGlobalError() {
        ObjectError objectError = new ObjectError(TEST_OBJECT_NAME, TEST_FIELD_ERROR_MESSAGE);
        when(bindingResult.getGlobalErrors()).thenReturn(singletonList(objectError));
    }

    @SuppressWarnings("all")
    private void addFieldError() {
        Object rejectedValue = null;
        boolean bindingFailure = false;
        Object[] arguments = null;
        String defaultMessage = null;

        FieldError fieldError = new FieldError(TEST_OBJECT_NAME, TEST_FIELD, rejectedValue, bindingFailure,
                new String[]{TEST_FIELD_ERROR_MESSAGE}, arguments, defaultMessage);
        when(bindingResult.getFieldErrors()).thenReturn(asList(fieldError));
    }

}