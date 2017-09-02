package pl.przygudzki.hourly.commons.commands;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public interface Validatable {

	String REQUIRED_FIELD = "is a required field and can't be empty";

	void validate(ValidationErrors errors);

	default boolean isNullOrEmpty(String s) {
		return s == null || s.trim().isEmpty();
	}

	class ValidationErrors {

		private Map<String, Set<String>> errors = new HashMap<>();

		public void add(String fieldName, String message) {
			Set<String> fieldErrors = errors.getOrDefault(fieldName, new HashSet<>());
			fieldErrors.add(message);
			errors.putIfAbsent(fieldName, fieldErrors);
		}

		public boolean isValid() {
			return errors.isEmpty();
		}

		public Map<String, Set<String>> getErrors() {
			return errors;
		}

	}

}
