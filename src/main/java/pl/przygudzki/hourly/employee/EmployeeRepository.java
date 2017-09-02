package pl.przygudzki.hourly.employee;

import java.util.List;

interface EmployeeRepository {

	void put(Employee employee);

	List<Employee> getAll();

}
