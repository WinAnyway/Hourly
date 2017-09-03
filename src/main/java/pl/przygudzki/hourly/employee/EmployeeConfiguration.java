package pl.przygudzki.hourly.employee;

class EmployeeConfiguration {

	EmployeeManager employeeManager(PositionManager positionManager) {
		EmployeeRepository employeeRepository = new InMemoryEmployeeRepository();
		return new StandardEmployeeManager(positionManager, employeeRepository);
	}

	PositionManager positionManager() {
		PositionRepository positionRepository = new InMemoryPositionRepository();
		return new StandardPositionManager(positionRepository);
	}

}
