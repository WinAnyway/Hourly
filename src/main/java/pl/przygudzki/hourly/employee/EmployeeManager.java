package pl.przygudzki.hourly.employee;

import pl.przygudzki.hourly.employee.dto.*;

import java.util.Collection;

public interface EmployeeManager {

	void addEmployee(AddEmployeeCommand cmd);

	EmployeeDto getEmployee(EmployeeId employeeId);

	Collection<EmployeeDto> listEmployees();

	void editEmployee(EmployeeId employeeId, EditEmployeeCommand command);

	void removeEmployee(EmployeeId employeeId);

}
