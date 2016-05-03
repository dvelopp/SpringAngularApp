package springAngularApp.system.ws.schema;

import java.util.List;
import java.util.Map;

public class ValidationErrorsResponse {

    private Map<String, List<String>> fieldErrors;
    private List<String> globalErrors;

    public ValidationErrorsResponse(Map<String, List<String>> fieldErrors, List<String> globalErrors) {
        this.fieldErrors = fieldErrors;
        this.globalErrors = globalErrors;
    }

    public Map<String, List<String>> getFieldErrors() {
        return fieldErrors;
    }

    public List<String> getGlobalErrors() {
        return globalErrors;
    }
}
