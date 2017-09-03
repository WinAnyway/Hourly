package pl.przygudzki.hourly.employee;

import pl.przygudzki.hourly.employee.dto.*;

import java.util.Collection;

public interface EmployeeManager {

	void addEmployee(AddEmployeeCommand cmd);

	Employee getEmployeeOrThrow(Long employeeId);

	Collection<EmployeeDto> listEmployees();

}
