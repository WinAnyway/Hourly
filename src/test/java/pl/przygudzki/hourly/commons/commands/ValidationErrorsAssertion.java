package pl.przygudzki.hourly.commons.commands;

import org.assertj.core.api.Assertions;

public class ValidationErrorsAssertion extends Assertions {

	public static ValidationErrorsPredicateAssert assertThat(Validatable.ValidationErrors actual) {
		return new ValidationErrorsPredicateAssert(actual);
	}

}
