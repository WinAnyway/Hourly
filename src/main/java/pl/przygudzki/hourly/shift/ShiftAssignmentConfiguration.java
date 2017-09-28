package pl.przygudzki.hourly.shift;

class ShiftAssignmentConfiguration {

	ShiftAssignmentManager shiftAssignmentManager() {
		ShiftAssignmentRepository repository = new ShiftAssignmentRepository();
		return new ShiftAssignmentManager(repository);
	}

}
