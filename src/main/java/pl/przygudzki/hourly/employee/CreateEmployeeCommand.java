package pl.przygudzki.hourly.employee;

import lombok.Getter;
import lombok.Setter;
import pl.przygudzki.hourly.commons.commands.Validatable;

@Getter
@Setter
public class CreateEmployeeCommand implements Validatable {

	private Position position;
	private String firstName;
	private String lastName;

	@Override
	public void validate(ValidationErrors errors) {
		validatePosition(errors);
		validateFirstName(errors);
		validateLastName(errors);
	}

	private void validateLastName(ValidationErrors errors) {
		if (isNullOrEmpty(lastName))
			errors.add("lastName", REQUIRED_FIELD);
	}

	private void validateFirstName(ValidationErrors errors) {
		if (isNullOrEmpty(firstName))
			errors.add("firstName", REQUIRED_FIELD);
	}

	private void validatePosition(ValidationErrors errors) {
		if (position == null || isNullOrEmpty(position.getName()))
			errors.add("position", REQUIRED_FIELD);
	}

}
