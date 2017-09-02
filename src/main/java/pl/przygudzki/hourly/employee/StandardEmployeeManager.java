package pl.przygudzki.hourly.employee;

import pl.przygudzki.hourly.commons.commands.InvalidCommandException;
import pl.przygudzki.hourly.commons.commands.Validatable;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

class StandardEmployeeManager implements EmployeeManager {

	private final EmployeeDtoBuilder employeeDtoBuilder = new EmployeeDtoBuilder();

	private EmployeeRepository employeeRepository;
	private PositionRepository positionRepository;

	StandardEmployeeManager(EmployeeRepository employeeRepository, PositionRepository positionRepository) {
		this.employeeRepository = employeeRepository;
		this.positionRepository = positionRepository;
	}

	@Override
	public void addEmployee(AddEmployeeCommand command) {
		validateCommand(command);
		command.setPosition(positionRepository.get(command.getPositionTitle()));
		Employee employee = Employee.create(command);
		employeeRepository.put(employee);
	}

	@Override
	public Collection<EmployeeDto> showEmployees() {
		return employeeRepository.findAll().stream()
				.map(employee -> {
					employee.export(employeeDtoBuilder);
					return employeeDtoBuilder.build();
				})
				.collect(Collectors.toList());
	}

	@Override
	public void addPosition(AddPositionCommand command) {
		validateCommand(command);
		Position position = Position.create(command);
		positionRepository.put(position);
	}

	@Override
	public List<Position> showPositions() {
		return positionRepository.getAll();
	}

	private void validateCommand(Validatable command) {
		Validatable.ValidationErrors errors = new Validatable.ValidationErrors();
		command.validate(errors);
		if (!errors.isValid())
			throw new InvalidCommandException(errors);
	}

}
