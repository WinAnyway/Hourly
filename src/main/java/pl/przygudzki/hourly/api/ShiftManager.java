package pl.przygudzki.hourly.api;

import pl.przygudzki.hourly.domain.commands.CreateShiftCommand;

public interface ShiftManager {

	void createShift(CreateShiftCommand command);

}
