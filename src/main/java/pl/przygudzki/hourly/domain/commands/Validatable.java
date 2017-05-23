package pl.przygudzki.hourly.domain.commands;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public interface Validatable {

    void validate(ValidationErrors errors);

    default boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    class ValidationErrors {

        private Map<String, Set<String>> errors = new HashMap<>();

        public void add(String fieldName, String message) {
            Set<String> fieldErrors = errors.getOrDefault(fieldName, new HashSet<>());
            fieldErrors.add(message);
            errors.putIfAbsent(fieldName, fieldErrors);
        }

        public boolean isValid() {return errors.isEmpty();}

        public Map<String, Set<String>> getErrors() {
            return errors;
        }
    }
}
