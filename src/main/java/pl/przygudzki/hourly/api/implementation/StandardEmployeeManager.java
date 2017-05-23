package pl.przygudzki.hourly.api.implementation;

import pl.przygudzki.hourly.api.EmployeeManager;
import pl.przygudzki.hourly.domain.Employee;
import pl.przygudzki.hourly.domain.EmployeeRepository;
import pl.przygudzki.hourly.domain.commands.CreateEmployeeCommand;
import pl.przygudzki.hourly.domain.commands.InvalidCommandException;
import pl.przygudzki.hourly.domain.commands.Validatable;

public class StandardEmployeeManager implements EmployeeManager {

    private EmployeeRepository employeeRepository;

    public StandardEmployeeManager(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void createEmployee(CreateEmployeeCommand cmd) {
        validateCommand(cmd);
        employeeRepository.put(new Employee(cmd));
    }

    private void validateCommand(CreateEmployeeCommand cmd) {
        Validatable.ValidationErrors errors = new Validatable.ValidationErrors();
        cmd.validate(errors);
        if (!errors.isValid())
            throw new InvalidCommandException(errors);
    }

}
