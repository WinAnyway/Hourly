package pl.przygudzki.hourly.employee;

import pl.przygudzki.hourly.employee.dto.AddEmployeeCommand;
import pl.przygudzki.hourly.employee.dto.AddPositionCommand;

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
