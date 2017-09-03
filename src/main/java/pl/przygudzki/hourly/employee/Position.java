package pl.przygudzki.hourly.employee;

import lombok.EqualsAndHashCode;
import pl.przygudzki.hourly.employee.dto.AddPositionCommand;

@EqualsAndHashCode
public class Position {

	private PositionId id;
	private String title;

	private Position(String position) {
		this.id = PositionId.generate();
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

	void export(PositionExporter exporter) {
		exporter.exportId(id);
		exporter.exportTitle(title);
	}

}
