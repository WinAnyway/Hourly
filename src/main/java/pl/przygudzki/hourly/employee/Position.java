package pl.przygudzki.hourly.employee;

import lombok.EqualsAndHashCode;
import pl.przygudzki.hourly.employee.dto.AddPositionCommand;
import pl.przygudzki.hourly.employee.dto.EditPositionCommand;

import static pl.przygudzki.hourly.employee.PositionStatus.*;

@EqualsAndHashCode
class Position {

	private PositionId id;
	private PositionStatus status;
	private String title;

	private Position(String position) {
		this.id = PositionId.generate();
		this.status = AVAILABLE;
		this.title = position;
	}

	static Position create(AddPositionCommand command) {
		return new Position(command.getTitle());
	}

	void edit(EditPositionCommand command) {
		this.title = command.getTitle();
	}

	void export(PositionExporter exporter) {
		exporter.exportId(id);
		exporter.exportTitle(title);
	}

	PositionId getId() {
		return id;
	}

	void remove() {
		status = REMOVED;
	}

	boolean isAvailable() {
		return status == AVAILABLE;
	}
}
