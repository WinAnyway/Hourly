package pl.przygudzki.hourly.shift;

class ShiftConfiguration {

	ShiftManager shiftManager() {
		ShiftRepository shiftRepository = new InMemoryShiftRepository();
		return new StandardShiftManager(shiftRepository);
	}

}
