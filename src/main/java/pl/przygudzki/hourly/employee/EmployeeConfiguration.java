package pl.przygudzki.hourly.employee;

import pl.przygudzki.hourly.position.PositionManager;

public class EmployeeConfiguration {

	public EmployeeManager employeeManager(PositionManager positionManager) {
		EmployeeRepository employeeRepository = new InMemoryEmployeeRepository();
		return new StandardEmployeeManager(positionManager, employeeRepository);
	}

}
