package pl.przygudzki.hourly.position.dto;

import pl.przygudzki.hourly.commons.commands.Validatable;

public class AddPositionCommand implements Validatable {

	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void validate(ValidationErrors errors) {
		if (isNullOrEmpty(title))
			errors.add("title", REQUIRED_FIELD);
	}

}
