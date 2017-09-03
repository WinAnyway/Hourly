package pl.przygudzki.hourly.employee;

import java.util.*;

class InMemoryEmployeeRepository implements EmployeeRepository {

	private Map<EmployeeId, Employee> employees = new HashMap<>();

	@Override
	public void put(Employee employee) {
		employees.put(employee.getId(), employee);
	}

	@Override
	public Optional<Employee> get(EmployeeId id) {
		return Optional.ofNullable(employees.get(id));
	}

	@Override
	public List<Employee> getAll() {
		return new LinkedList<>(employees.values());
	}

}
