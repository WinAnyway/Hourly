package pl.przygudzki.hourly.employee;

import java.util.Set;

interface EmployeeRepository {

	void save(Employee employee);

	Set<EmployeeDto> findAll();

}
