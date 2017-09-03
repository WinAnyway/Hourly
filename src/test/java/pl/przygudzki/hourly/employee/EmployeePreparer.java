package pl.przygudzki.hourly.employee;

import pl.przygudzki.hourly.employee.dto.AddEmployeeCommand;
import pl.przygudzki.hourly.employee.dto.AddPositionCommand;
import pl.przygudzki.hourly.employee.dto.EditPositionCommand;
import pl.przygudzki.hourly.employee.dto.PositionDto;

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

	EditPositionCommand editPositionCommandFromPositionDto(PositionDto positionDto) {
		EditPositionCommand command = new EditPositionCommand();
		command.setTitle(positionDto.getTitle());
		return command;
	}

}
