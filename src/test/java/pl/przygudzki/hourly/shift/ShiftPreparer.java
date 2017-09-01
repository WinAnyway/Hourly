package pl.przygudzki.hourly.shift;

import java.time.LocalDateTime;

public class ShiftPreparer {

	public CreateShiftCommand validCreateShiftCommand() {
		CreateShiftCommand command = new CreateShiftCommand();
		command.setStartDate(LocalDateTime.of(2017, 7, 1, 12, 0));
		command.setEndDate(LocalDateTime.of(2017, 7, 1, 16, 0));
		return command;
	}

}
