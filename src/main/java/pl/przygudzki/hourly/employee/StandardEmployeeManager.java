package pl.przygudzki.hourly.employee;

import pl.przygudzki.hourly.commons.commands.InvalidCommandException;
import pl.przygudzki.hourly.commons.commands.Validatable;
import pl.przygudzki.hourly.employee.dto.*;
import pl.przygudzki.hourly.position.PositionId;
import pl.przygudzki.hourly.position.PositionManager;
import pl.przygudzki.hourly.position.dto.PositionNotAvailableException;

import java.util.Collection;
import java.util.stream.Collectors;

class StandardEmployeeManager implements EmployeeManager {

	private EmployeeRepository employeeRepository;
	private PositionManager positionManager;

	private final EmployeeDtoBuilder dtoBuilder;

	StandardEmployeeManager(PositionManager positionManager, EmployeeRepository employeeRepository) {
		this.positionManager = positionManager;
		this.employeeRepository = employeeRepository;
		this.dtoBuilder = new EmployeeDtoBuilder(positionManager);
	}

	@Override
	public void addEmployee(AddEmployeeCommand command) {
		validateCommand(command);
		verifyPositionAvailabilityOrThrow(command.getPositionId());
		Employee employee = Employee.create(command);
		employeeRepository.put(employee);
	}

	@Override
	public EmployeeDto getEmployee(EmployeeId employeeId) {
		Employee employee = getOrThrow(employeeId);
		return buildEmployeeDto(employee);
	}

	@Override
	public Collection<EmployeeDto> listEmployees() {
		return employeeRepository.getAll().stream()
				.filter(Employee::isAvailable)
				.map(this::buildEmployeeDto)
				.collect(Collectors.toList());
	}

	@Override
	public void editEmployee(EmployeeId employeeId, EditEmployeeCommand command) {
		validateCommand(command);
		verifyPositionAvailabilityOrThrow(command.getPositionId());
		Employee employee = getOrThrow(employeeId);
		employee.edit(command);
	}

	@Override
	public void removeEmployee(EmployeeId employeeId) {
		Employee employee = getOrThrow(employeeId);
		employee.remove();
	}

	private Employee getOrThrow(EmployeeId employeeId) {
		return employeeRepository.get(employeeId)
				.orElseThrow(() -> new EmployeeNotFoundException(employeeId));
	}

	private void verifyPositionAvailabilityOrThrow(PositionId positionId) {
		if (! positionManager.isAvailable(positionId))
			throw new PositionNotAvailableException(positionId);
	}

	private EmployeeDto buildEmployeeDto(Employee employee) {
		employee.export(dtoBuilder);
		return dtoBuilder.build();
	}

	private void validateCommand(Validatable command) {
		Validatable.ValidationErrors errors = new Validatable.ValidationErrors();
		command.validate(errors);
		if (!errors.isValid())
			throw new InvalidCommandException(errors);
	}

}
