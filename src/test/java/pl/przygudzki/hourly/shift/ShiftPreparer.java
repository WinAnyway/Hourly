package pl.przygudzki.hourly.shift;

import pl.przygudzki.hourly.shift.dto.AddShiftCommand;
import pl.przygudzki.hourly.shift.dto.ShiftDto;

import java.util.Collection;

class ShiftPreparer {

	private final ShiftManager shiftManager;
	private CommandPreparer given = new CommandPreparer();

	ShiftPreparer(ShiftManager shiftManager) {
		this.shiftManager = shiftManager;
	}

	ShiftDto newShiftIsAdded() {
		return newShiftIsAdded(given.validAddShiftCommand());
	}

	ShiftDto newShiftIsAdded(AddShiftCommand command) {
		Collection<ShiftDto> shiftsBefore = shiftManager.listShifts();
		shiftManager.addShift(command);
		return shiftManager.listShifts().stream()
				.filter(shiftDto -> !shiftsBefore.contains(shiftDto))
				.findAny()
				.orElseThrow(IllegalStateException::new);
	}

	ShiftId newRemovedShift() {
		ShiftId shiftId = newShiftIsAdded().getId();
		shiftManager.remove(shiftId);
		return shiftId;
	}
}
