package pl.przygudzki.hourly.shift;

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
