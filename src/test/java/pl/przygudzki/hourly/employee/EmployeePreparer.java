package pl.przygudzki.hourly.employee;

import lombok.AllArgsConstructor;
import pl.przygudzki.hourly.employee.dto.EmployeeDto;
import pl.przygudzki.hourly.employee.dto.PositionDto;

import java.util.Collection;

@AllArgsConstructor
public class EmployeePreparer {

	private final EmployeeManager employeeManager;
	private final PositionManager positionManager;

	private final CommandPreparer given = new CommandPreparer();

	public static EmployeePreparer withInternalizedDependencies() {
		EmployeeConfiguration configuration = new EmployeeConfiguration();
		PositionManager positionManager = configuration.positionManager();
		EmployeeManager employeeManager = configuration.employeeManager(positionManager);
		return new EmployeePreparer(employeeManager, positionManager);
	}

	PositionDto newPositionIsAdded() {
		Collection<PositionDto> beforePositions = positionManager.listPositions();
		positionManager.addPosition(given.validAddPositionCommand());
		return positionManager.listPositions().stream()
				.filter(positionDto -> !beforePositions.contains(positionDto))
				.findAny().orElseThrow(IllegalStateException::new);
	}

	EmployeeDto newEmployeeIsAdded(Long positionId) {
		Collection<EmployeeDto> beforeEmployees = employeeManager.listEmployees();
		employeeManager.addEmployee(given.validAddEmployeeCommand(positionId));
		return employeeManager.listEmployees().stream()
				.filter(employeeDto -> !beforeEmployees.contains(employeeDto))
				.findAny().orElseThrow(IllegalStateException::new);
	}

}
