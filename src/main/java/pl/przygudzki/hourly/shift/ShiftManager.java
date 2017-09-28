package pl.przygudzki.hourly.shift;

import pl.przygudzki.hourly.shift.dto.AddShiftCommand;
import pl.przygudzki.hourly.shift.dto.EditShiftCommand;
import pl.przygudzki.hourly.shift.dto.ShiftDto;

import java.util.Collection;

public interface ShiftManager {

	void addShift(AddShiftCommand command);

	Collection<ShiftDto> listShifts();

	ShiftDto getShift(ShiftId shiftId);

	void editShift(ShiftId id, EditShiftCommand command);

	void remove(ShiftId shiftId);

}
