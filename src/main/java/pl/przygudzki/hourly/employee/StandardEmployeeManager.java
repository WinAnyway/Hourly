package pl.przygudzki.hourly.employee;

import pl.przygudzki.hourly.commons.commands.InvalidCommandException;
import pl.przygudzki.hourly.commons.commands.Validatable;

import java.util.Set;

class StandardEmployeeManager implements EmployeeManager {

	private EmployeeRepository employeeRepository;

	StandardEmployeeManager(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public void addEmployee(AddEmployeeCommand command) {
		validateCommand(command);
		Employee employee = Employee.create(command);
		employeeRepository.save(employee);
	}

	@Override
	public Set<EmployeeDto> show() {
		return employeeRepository.findAll();
	}

	private void validateCommand(AddEmployeeCommand cmd) {
		Validatable.ValidationErrors errors = new Validatable.ValidationErrors();
		cmd.validate(errors);
		if (!errors.isValid())
			throw new InvalidCommandException(errors);
	}

}
