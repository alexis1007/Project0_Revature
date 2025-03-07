package Application.Util;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    private List<String> errors;
    
    public ValidationResult() {
        this.errors = new ArrayList<>();
    }

    public void addError(String error) {
        errors.add(error);
    }

    public List<String> getErrors() {
        return errors;
    }

    public boolean isValid() {
        return errors.isEmpty();
    }
}