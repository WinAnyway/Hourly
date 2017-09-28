package pl.przygudzki.hourly.shift;

import pl.przygudzki.hourly.shift.dto.AddShiftCommand;
import pl.przygudzki.hourly.shift.dto.EditShiftCommand;
import pl.przygudzki.hourly.shift.dto.ShiftDto;

import java.time.LocalDateTime;

public class CommandPreparer {

	private static final LocalDateTime START_DATE = LocalDateTime.of(2017, 7, 1, 8, 0);
	private static final LocalDateTime END_DATE = LocalDateTime.of(2017, 7, 1, 16, 0);
	private static final LocalDateTime ANOTHER_START_DATE = LocalDateTime.of(2017, 7, 1, 16, 0);
	private static final LocalDateTime ANOTHER_END_DATE = LocalDateTime.of(2017, 7, 2, 0, 0);
	private static final ShiftId INVALID_SHIFT_ID = ShiftId.of(-123L);

	ShiftId invalidShiftId() {
		return INVALID_SHIFT_ID;
	}

	AddShiftCommand validAddShiftCommand() {
		return createAddShiftCommand(START_DATE, END_DATE);
	}

	AddShiftCommand anotherValidAddShiftCommand() {
		return createAddShiftCommand(ANOTHER_START_DATE, ANOTHER_END_DATE);
	}

	AddShiftCommand invalidAddShiftCommand() {
		return createAddShiftCommand(END_DATE, START_DATE);
	}

	AddShiftCommand addCommandWithDatesOutOfOrder() {
		return createAddShiftCommand(END_DATE, START_DATE);
	}

	EditShiftCommand editShiftCommandFromDto(ShiftDto shiftDto) {
		return createEditShiftCommand(shiftDto.getStartDate(), shiftDto.getEndDate());
	}

	EditShiftCommand validEditShiftCommand() {
		return createEditShiftCommand(ANOTHER_START_DATE, ANOTHER_END_DATE);
	}

	EditShiftCommand editCommandWithDatesOutOfOrder() {
		return createEditShiftCommand(END_DATE, START_DATE);
	}

	private AddShiftCommand createAddShiftCommand(LocalDateTime startDate, LocalDateTime endDate) {
		AddShiftCommand command = new AddShiftCommand();
		command.setStartDate(startDate);
		command.setEndDate(endDate);
		return command;
	}

	private EditShiftCommand createEditShiftCommand(LocalDateTime startDate, LocalDateTime endDate) {
		EditShiftCommand command = new EditShiftCommand();
		command.setStartDate(startDate);
		command.setEndDate(endDate);
		return command;
	}

}
