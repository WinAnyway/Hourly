package pl.przygudzki.hourly.employee;

class EmployeeConfiguration {

	EmployeeManager employeeManager() {
		EmployeeRepository employeeRepository = new InMemoryEmployeeRepository();
		PositionRepository positionRepository = new InMemoryPositionRepository();
		return new StandardEmployeeManager(employeeRepository, positionRepository);
	}

}
