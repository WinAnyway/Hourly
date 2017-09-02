package pl.przygudzki.hourly.employee;

class EmployeePreparer {

	AddEmployeeCommand validAddEmployeeCommand() {
		AddEmployeeCommand command = new AddEmployeeCommand();
		command.setPositionTitle("Manager");
		command.setFirstName("John");
		command.setLastName("Doe");
		return command;
	}

	AddPositionCommand validAddPositionCommand() {
		AddPositionCommand command = new AddPositionCommand();
		command.setTitle("Manager");
		return command;
	}

}
