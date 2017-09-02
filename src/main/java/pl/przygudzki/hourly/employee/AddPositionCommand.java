package pl.przygudzki.hourly.employee;

import pl.przygudzki.hourly.commons.commands.Validatable;

class AddPositionCommand implements Validatable {

	private String title;

	String getTitle() {
		return title;
	}

	void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void validate(ValidationErrors errors) {
		if(isNullOrEmpty(title))
			errors.add("name", REQUIRED_FIELD);
	}

}
