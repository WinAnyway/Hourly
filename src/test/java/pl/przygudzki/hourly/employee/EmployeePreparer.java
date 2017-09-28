package pl.przygudzki.hourly.employee;

import lombok.AllArgsConstructor;
import pl.przygudzki.hourly.employee.dto.AddEmployeeCommand;
import pl.przygudzki.hourly.employee.dto.EmployeeDto;
import pl.przygudzki.hourly.position.PositionId;
import pl.przygudzki.hourly.position.PositionPreparer;

import java.util.Collection;

@AllArgsConstructor
public class EmployeePreparer {

	private final EmployeeManager employeeManager;

	private final PositionPreparer givenPosition;
	private final EmployeeCommandPreparer given = new EmployeeCommandPreparer();

	public EmployeeDto newEmployeeIsAdded() {
		PositionId positionId = givenPosition.newPositionIsAdded().getId();
		return newEmployeeIsAdded(given.validAddEmployeeCommand(positionId));
	}

	EmployeeDto newEmployeeIsAdded(PositionId positionId) {
		return newEmployeeIsAdded(given.validAddEmployeeCommand(positionId));
	}

	EmployeeDto newEmployeeIsAdded(AddEmployeeCommand command) {
		Collection<EmployeeDto> beforeEmployees = employeeManager.listEmployees();
		employeeManager.addEmployee(command);
		return employeeManager.listEmployees().stream()
				.filter(employeeDto -> !beforeEmployees.contains(employeeDto))
				.findAny().orElseThrow(IllegalStateException::new);
	}

	EmployeeId newRemovedEmployee(PositionId positionId) {
		EmployeeId employeeId = newEmployeeIsAdded(positionId).getId();
		employeeManager.removeEmployee(employeeId);
		return employeeId;
	}

}
