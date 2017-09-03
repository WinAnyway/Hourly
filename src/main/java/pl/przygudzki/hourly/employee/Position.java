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

	static Position create(AddPositionCommand command) {
		return new Position(command.getTitle());
	}

	void export(PositionExporter exporter) {
		exporter.exportId(id);
		exporter.exportTitle(title);
	}

	PositionId getId() {
		return id;
	}

}
