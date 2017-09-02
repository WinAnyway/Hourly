package pl.przygudzki.hourly.employee;

class EmployeeConfiguration {

	EmployeeManager employeeManager() {
		EmployeeRepository employeeRepository = new InMemoryEmployeeRepository();
		return new StandardEmployeeManager(employeeRepository);
	}

}
