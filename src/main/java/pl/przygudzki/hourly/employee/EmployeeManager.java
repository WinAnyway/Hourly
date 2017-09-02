package pl.przygudzki.hourly.employee;

import java.util.Set;

public interface EmployeeManager {

	void addEmployee(AddEmployeeCommand cmd);

	Set<EmployeeDto> show();

}
