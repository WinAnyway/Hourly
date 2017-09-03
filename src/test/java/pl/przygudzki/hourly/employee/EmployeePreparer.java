package pl.przygudzki.hourly.employee;

import lombok.AllArgsConstructor;
import pl.przygudzki.hourly.employee.dto.AddEmployeeCommand;
import pl.przygudzki.hourly.employee.dto.AddPositionCommand;
import pl.przygudzki.hourly.employee.dto.EditPositionCommand;
import pl.przygudzki.hourly.employee.dto.PositionDto;

import java.util.Collection;

@AllArgsConstructor
public class EmployeePreparer {

	private final EmployeeManager employeeManager;

	PositionDto newPositionIsAdded() {
		Collection<PositionDto> beforePositions = employeeManager.listPositions();
		employeeManager.addPosition(validAddPositionCommand());
		return employeeManager.listPositions().stream()
				.filter(positionDto -> !beforePositions.contains(positionDto))
				.findAny().orElseThrow(IllegalStateException::new);
	}

	AddEmployeeCommand validAddEmployeeCommand(Long positionId) {
		return createAddEmployeeCommand(positionId, "John", "Doe");
	}

	AddEmployeeCommand validAddEmployeeCommand() {
		return createAddEmployeeCommand(123L, "John", "Doe");
	}

	AddEmployeeCommand anotherValidAddEmployeeCommand() {
		return createAddEmployeeCommand(123L, "Jane", "Dean");
	}

	AddPositionCommand validAddPositionCommand() {
		return createAddPositionCommand("Manager");
	}

	AddPositionCommand anotherValidAddPositionCommand() {
		return createAddPositionCommand("Copywriter");
	}

	EditPositionCommand validEditPositionCommand() {
		return createEditPositionCommand("Barista");
	}

	EditPositionCommand editPositionCommandFromPositionDto(PositionDto positionDto) {
		return createEditPositionCommand(positionDto.getTitle());
	}

	private AddEmployeeCommand createAddEmployeeCommand(Long positionId, String firstName, String lastName) {
		AddEmployeeCommand command = new AddEmployeeCommand();
		command.setPositionId(positionId);
		command.setFirstName(firstName);
		command.setLastName(lastName);
		return command;
	}

	private AddPositionCommand createAddPositionCommand(String title) {
		AddPositionCommand command = new AddPositionCommand();
		command.setTitle(title);
		return command;
	}

	private EditPositionCommand createEditPositionCommand(String title) {
		EditPositionCommand command = new EditPositionCommand();
		command.setTitle(title);
		return command;
	}

}
