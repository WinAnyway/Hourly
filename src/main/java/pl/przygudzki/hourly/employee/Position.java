package pl.przygudzki.hourly.employee;

import lombok.EqualsAndHashCode;
import pl.przygudzki.hourly.employee.dto.AddPositionCommand;

@EqualsAndHashCode
public class Position {

	private final String title;

	private Position(String position) {
		this.title = position;
	}

	String getTitle() {
		return title;
	}

	static Position create(AddPositionCommand command) {
		return new Position(command.getTitle());
	}

	static Position of(String title) {
		return new Position(title);
	}

}
