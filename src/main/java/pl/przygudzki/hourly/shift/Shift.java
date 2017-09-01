package pl.przygudzki.hourly.shift;

import java.time.LocalDateTime;

class Shift {

	private ShiftId id;
	private LocalDateTime startDate;
	private LocalDateTime endDate;

	private Shift(ShiftId id, LocalDateTime startDate, LocalDateTime endDate) {
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	static Shift create(CreateShiftCommand command) {
		ShiftId id = ShiftId.generate();
		LocalDateTime startDate = command.getStartDate();
		LocalDateTime endDate = command.getEndDate();
		if (!startDate.isBefore(endDate))
			throw new IllegalArgumentException("startDate must be earlier than endDate");
		return new Shift(id, startDate, endDate);
	}

	ShiftId getId() {
		return id;
	}
}
