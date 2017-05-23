package pl.przygudzki.hourly.api.implementation;

import pl.przygudzki.hourly.api.ShiftManager;
import pl.przygudzki.hourly.domain.commands.CreateShiftCommand;
import pl.przygudzki.hourly.domain.shift.Shift;
import pl.przygudzki.hourly.domain.shift.ShiftRepository;

public class StandardShiftManager implements ShiftManager {

	private ShiftRepository shiftRepository;

	public StandardShiftManager(ShiftRepository shiftRepository) {
		this.shiftRepository = shiftRepository;
	}

	@Override
	public void createShift(CreateShiftCommand command) {
		Shift shift = Shift.create(command);
		shiftRepository.put(shift);
	}

}
