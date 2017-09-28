package pl.przygudzki.hourly.position.dto;

import lombok.Getter;
import lombok.Setter;
import pl.przygudzki.hourly.commons.commands.Validatable;

@Getter
@Setter
public class EditPositionCommand implements Validatable {

	private String title;

	@Override
	public void validate(ValidationErrors errors) {
		if (isNullOrEmpty(title))
			errors.add("title", REQUIRED_FIELD);
	}

}