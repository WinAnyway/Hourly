package pl.przygudzki.hourly.employee.dto;

import lombok.Getter;
import lombok.Setter;
import pl.przygudzki.hourly.commons.commands.Validatable;
import pl.przygudzki.hourly.position.PositionId;

@Getter
@Setter
public class EditEmployeeCommand implements Validatable {

	private PositionId positionId;
	private String firstName;
	private String lastName;

	@Override
	public void validate(ValidationErrors errors) {
		validatePosition(errors);
		validateFirstName(errors);
		validateLastName(errors);
	}

	private void validateFirstName(ValidationErrors errors) {
		if (isNullOrEmpty(firstName))
			errors.add("firstName", REQUIRED_FIELD);
	}

	private void validateLastName(ValidationErrors errors) {
		if (isNullOrEmpty(lastName))
			errors.add("lastName", REQUIRED_FIELD);
	}

	private void validatePosition(ValidationErrors errors) {
		if (isNull(positionId))
			errors.add("positionId", REQUIRED_FIELD);
	}

}
