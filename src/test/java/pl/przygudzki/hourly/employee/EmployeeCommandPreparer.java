package pl.przygudzki.hourly.employee;

import pl.przygudzki.hourly.employee.dto.*;
import pl.przygudzki.hourly.position.PositionCommandPreparer;
import pl.przygudzki.hourly.position.PositionId;
import pl.przygudzki.hourly.position.dto.AddPositionCommand;
import pl.przygudzki.hourly.position.dto.EditPositionCommand;
import pl.przygudzki.hourly.position.dto.PositionDto;

public class EmployeeCommandPreparer {

	private static final PositionId VALID_POSITION_ID = PositionCommandPreparer.validPositionId();
	private static final PositionId NON_EXISTENT_POSITION_ID = PositionCommandPreparer.nonExistentPositionId();
	private static final EmployeeId VALID_EMPLOYEE_ID = EmployeeId.of(123L);
	private static final EmployeeId NON_EXISTENT_EMPLOYEE_ID = EmployeeId.of(-123L);

	PositionId validPositionId() {
		return VALID_POSITION_ID;
	}

	PositionId nonExistentPositionId() {
		return NON_EXISTENT_POSITION_ID;
	}

	EmployeeId validEmployeeId() {
		return VALID_EMPLOYEE_ID;
	}

	EmployeeId nonExistentEmployeeId() {
		return NON_EXISTENT_EMPLOYEE_ID;
	}

	AddEmployeeCommand validAddEmployeeCommand() {
		return createAddEmployeeCommand(VALID_POSITION_ID, "John", "Doe");
	}

	AddEmployeeCommand invalidAddEmployeeCommand() {
		return createAddEmployeeCommand(null, "", "");
	}

	AddEmployeeCommand validAddEmployeeCommand(PositionId positionId) {
		return createAddEmployeeCommand(positionId, "John", "Doe");
	}

	AddEmployeeCommand anotherValidAddEmployeeCommand(PositionId positionId) {
		return createAddEmployeeCommand(positionId, "Jane", "Dean");
	}

	EditEmployeeCommand validEditEmployeeCommand() {
		return validEditEmployeeCommand(VALID_POSITION_ID);
	}

	EditEmployeeCommand validEditEmployeeCommand(PositionId positionId) {
		return createEditEmployeeCommand(positionId, "Jeremy", "Dane");
	}

	EditEmployeeCommand invalidEditEmployeeCommand() {
		return createEditEmployeeCommand(VALID_POSITION_ID, "", "");
	}

	EditEmployeeCommand editEmployeeCommandFrom(EmployeeDto dto) {
		return createEditEmployeeCommand(dto.getPosition().getId(), dto.getFirstName(), dto.getLastName());
	}

	private AddEmployeeCommand createAddEmployeeCommand(PositionId positionId, String firstName, String lastName) {
		AddEmployeeCommand command = new AddEmployeeCommand();
		command.setPositionId(positionId);
		command.setFirstName(firstName);
		command.setLastName(lastName);
		return command;
	}

	private EditEmployeeCommand createEditEmployeeCommand(PositionId positonId, String firstName, String lastName) {
		EditEmployeeCommand command = new EditEmployeeCommand();
		command.setPositionId(positonId);
		command.setFirstName(firstName);
		command.setLastName(lastName);
		return command;
	}

}
