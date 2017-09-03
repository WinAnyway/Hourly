package pl.przygudzki.hourly.employee;

import pl.przygudzki.hourly.employee.dto.AddEmployeeCommand;
import pl.przygudzki.hourly.employee.dto.AddPositionCommand;

class EmployeePreparer {

	AddEmployeeCommand validAddEmployeeCommand() {
		AddEmployeeCommand command = new AddEmployeeCommand();
		command.setPositionId(1L);
		command.setFirstName("John");
		command.setLastName("Doe");
		return command;
	}

	AddPositionCommand validAddPositionCommand() {
		AddPositionCommand command = new AddPositionCommand();
		command.setTitle("Manager");
		return command;
	}

	AddPositionCommand anotherValidAddPositionCommand() {
		AddPositionCommand command = new AddPositionCommand();
		command.setTitle("Copywriter");
		return command;
	}

}
