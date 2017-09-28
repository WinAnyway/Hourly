package pl.przygudzki.hourly.position;

import lombok.EqualsAndHashCode;
import pl.przygudzki.hourly.position.dto.AddPositionCommand;
import pl.przygudzki.hourly.position.dto.EditPositionCommand;

@EqualsAndHashCode
class Position {

	private PositionId id;
	private PositionStatus status;
	private String title;

	private Position(String position) {
		this.id = PositionId.generate();
		this.status = PositionStatus.AVAILABLE;
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
		exporter.exportStatus(status);
	}

	PositionId getId() {
		return id;
	}

	void remove() {
		status = PositionStatus.REMOVED;
	}

	boolean isAvailable() {
		return status == PositionStatus.AVAILABLE;
	}

}
