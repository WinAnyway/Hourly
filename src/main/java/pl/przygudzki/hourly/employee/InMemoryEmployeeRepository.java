package pl.przygudzki.hourly.employee;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

class InMemoryEmployeeRepository implements EmployeeRepository {

	private HashMap<EmployeeId, Employee> employees = new HashMap();

	@Override
	public void put(Employee employee) {
		employees.put(employee.getId(), employee);
	}

	@Override
	public List<Employee> getAll() {
		return new LinkedList<>(employees.values());
	}

}
