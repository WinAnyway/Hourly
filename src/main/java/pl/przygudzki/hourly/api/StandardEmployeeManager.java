package pl.przygudzki.hourly.api;

import pl.przygudzki.hourly.domain.Employee;
import pl.przygudzki.hourly.domain.EmployeeRepository;
import pl.przygudzki.hourly.domain.commands.CreateEmployeeCommand;

public class StandardEmployeeManager implements EmployeeManager {

    private EmployeeRepository employeeRepository;

    public StandardEmployeeManager(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void createEmployee(CreateEmployeeCommand cmd) {
        employeeRepository.put(new Employee(cmd));
    }

}
