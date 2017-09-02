package pl.przygudzki.hourly.commons.commands;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ValidationErrorsPredicateAssert {

	private final Validatable.ValidationErrors actual;

	private Long expectedNumber;

	public ValidationErrorsPredicateAssert isValid() {
		assertThat(actual.isValid()).isTrue();
		return this;
	}

	public ValidationErrorsPredicateAssert isNotValid() {
		assertThat(actual.isValid()).isFalse();
		return this;
	}

	public ValidationErrorsPredicateAssert hasError(String field, String error) {
		assertThat(actual.getErrors()).containsKey(field);
		assertThat(actual.getErrors().get(field)).contains(error);
		return this;
	}

	public ValidationErrorsPredicateAssert hasExactly(long expectedNumber) {
		this.expectedNumber = expectedNumber;
		return this;
	}

	public ValidationErrorsPredicateAssert errors(String field, String error) {
		if (expectedNumber != null)
			assertThat(this.countErrors()).isEqualTo(expectedNumber);
		return hasError(field, error);
	}

	private long countErrors() {
		return actual.getErrors().values().stream()
				.mapToLong(Set::size)
				.sum();
	}

}
