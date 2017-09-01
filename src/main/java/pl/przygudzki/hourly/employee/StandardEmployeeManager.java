package pl.przygudzki.hourly.employee;

import pl.przygudzki.hourly.commands.InvalidCommandException;
import pl.przygudzki.hourly.commands.Validatable;

public class StandardEmployeeManager implements EmployeeManager {

	private EmployeeRepository employeeRepository;

	public StandardEmployeeManager(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public void createEmployee(CreateEmployeeCommand command) {
		validateCommand(command);
		Employee employee = Employee.create(command);
		employeeRepository.put(employee);
	}

	private void validateCommand(CreateEmployeeCommand cmd) {
		Validatable.ValidationErrors errors = new Validatable.ValidationErrors();
		cmd.validate(errors);
		if (!errors.isValid())
			throw new InvalidCommandException(errors);
	}

}