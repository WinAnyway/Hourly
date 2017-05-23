package pl.przygudzki.hourly.domain.shift;

import pl.przygudzki.hourly.domain.commands.CreateShiftCommand;

import javax.persistence.EmbeddedId;
import java.time.LocalDateTime;

public class Shift {

	@EmbeddedId
	private ShiftId id;

	private LocalDateTime startDate;
	private LocalDateTime endDate;

	private Shift(ShiftId id, LocalDateTime startDate, LocalDateTime endDate) {
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public static Shift create(CreateShiftCommand command) {
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
