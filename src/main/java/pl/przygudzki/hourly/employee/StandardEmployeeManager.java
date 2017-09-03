package pl.przygudzki.hourly.employee;

import pl.przygudzki.hourly.commons.commands.InvalidCommandException;
import pl.przygudzki.hourly.commons.commands.Validatable;
import pl.przygudzki.hourly.employee.dto.*;

import java.util.Collection;
import java.util.stream.Collectors;

class StandardEmployeeManager implements EmployeeManager {

	private PositionManager positionManager;
	private EmployeeRepository employeeRepository;

	StandardEmployeeManager(PositionManager positionManager, EmployeeRepository employeeRepository) {
		this.positionManager = positionManager;
		this.employeeRepository = employeeRepository;
	}

	@Override
	public void addEmployee(AddEmployeeCommand command) {
		validateCommand(command);
		Position position = positionManager.getPositionOrThrow(command.getPositionId());
		Employee employee = Employee.create(command, position);
		employeeRepository.put(employee);
	}

	@Override
	public Employee getEmployeeOrThrow(Long employeeId) {
		return employeeRepository.get(EmployeeId.of(employeeId))
				.orElseThrow(() -> new EmployeeNotFoundException(employeeId));
	}

	@Override
	public Collection<EmployeeDto> listEmployees() {
		final EmployeeDtoBuilder dtoBuilder = new EmployeeDtoBuilder();
		return employeeRepository.getAll().stream()
				.map(employee -> {
					employee.export(dtoBuilder);
					return dtoBuilder.build();
				})
				.collect(Collectors.toList());
	}

	private void validateCommand(Validatable command) {
		Validatable.ValidationErrors errors = new Validatable.ValidationErrors();
		command.validate(errors);
		if (!errors.isValid())
			throw new InvalidCommandException(errors);
	}

}
