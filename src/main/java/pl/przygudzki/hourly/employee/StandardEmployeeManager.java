package pl.przygudzki.hourly.employee;

import pl.przygudzki.hourly.commons.commands.InvalidCommandException;
import pl.przygudzki.hourly.commons.commands.Validatable;
import pl.przygudzki.hourly.employee.dto.*;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

class StandardEmployeeManager implements EmployeeManager {

	private EmployeeRepository employeeRepository;
	private PositionRepository positionRepository;

	StandardEmployeeManager(EmployeeRepository employeeRepository, PositionRepository positionRepository) {
		this.employeeRepository = employeeRepository;
		this.positionRepository = positionRepository;
	}

	@Override
	public void addEmployee(AddEmployeeCommand command) {
		validateCommand(command);
		Position position = getPositionOrThrow(command.getPositionId());
		Employee employee = Employee.create(command, position);
		employeeRepository.put(employee);
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

	@Override
	public void addPosition(AddPositionCommand command) {
		validateCommand(command);
		Position position = Position.create(command);
		positionRepository.put(position);
	}

	private Position getPositionOrThrow(Long positionId) {
		return positionRepository.get(PositionId.of(positionId))
				.orElseThrow(() -> new PositionNotFoundException(positionId));
	}

	@Override
	public Collection<PositionDto> listPositions() {
		final PositionDtoBuilder dtoBuilder = new PositionDtoBuilder();
		return positionRepository.getAll().stream()
				.map(position -> {
					position.export(dtoBuilder);
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
