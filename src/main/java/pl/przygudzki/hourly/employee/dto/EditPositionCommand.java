package pl.przygudzki.hourly.employee.dto;

import lombok.Getter;
import lombok.Setter;
import pl.przygudzki.hourly.commons.commands.Validatable;

@Getter
@Setter
public class EditPositionCommand implements Validatable {

	private String title;

	@Override
	public void validate(ValidationErrors errors) {

	}

}
