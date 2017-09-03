package pl.przygudzki.hourly.employee;

import java.util.List;
import java.util.Optional;

interface EmployeeRepository {

	void put(Employee employee);

	Optional<Employee> get(EmployeeId of);

	List<Employee> getAll();

}
