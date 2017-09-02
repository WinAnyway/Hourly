package pl.przygudzki.hourly.employee;

import java.util.HashSet;
import java.util.Set;

class InMemoryEmployeeRepository implements EmployeeRepository {

	private Set<EmployeeDto> employees = new HashSet<>();

	@Override
	public void save(Employee employee) {
		EmployeeDtoBuilder exporter = new EmployeeDtoBuilder();
		employee.export(exporter);
		EmployeeDto employeeDto = exporter.build();
		employees.add(employeeDto);
	}

	@Override
	public Set<EmployeeDto> findAll() {
		return employees;
	}

}
