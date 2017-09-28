package pl.przygudzki.hourly.shift;

import pl.przygudzki.hourly.shift.dto.AddShiftCommand;
import pl.przygudzki.hourly.shift.dto.EditShiftCommand;

import java.time.Duration;
import java.time.LocalDateTime;

import static pl.przygudzki.hourly.shift.ShiftStatus.AVAILABLE;
import static pl.przygudzki.hourly.shift.ShiftStatus.REMOVED;

class Shift {

	private ShiftId id;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private ShiftStatus status;

	private Shift(ShiftId id, ShiftStatus status, LocalDateTime startDate, LocalDateTime endDate) {
		this.id = id;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	static Shift create(AddShiftCommand command) {
		ShiftId id = ShiftId.generate();
		LocalDateTime startDate = command.getStartDate();
		LocalDateTime endDate = command.getEndDate();
		verifyThatDatesAreInProperOrder(startDate, endDate);
		return new Shift(id, AVAILABLE, startDate, endDate);
	}

	void edit(EditShiftCommand command) {
		LocalDateTime startDate = command.getStartDate();
		LocalDateTime endDate = command.getEndDate();
		verifyThatDatesAreInProperOrder(startDate, endDate);
		this.startDate = startDate;
		this.endDate = endDate;
	}

	void remove() {
		this.status = REMOVED;
	}

	boolean isAvailable() {
		return status == AVAILABLE;
	}

	private static void verifyThatDatesAreInProperOrder(LocalDateTime startDate, LocalDateTime endDate) {
		if (!startDate.isBefore(endDate))
			throw new IllegalArgumentException("startDate must be earlier than endDate");
	}

	void export(ShiftExporter exporter) {
		exporter.exportId(id);
		exporter.exportStatus(status);
		exporter.exportStartDate(startDate);
		exporter.exportEndDate(endDate);
	}

	ShiftId getId() {
		return id;
	}

}
