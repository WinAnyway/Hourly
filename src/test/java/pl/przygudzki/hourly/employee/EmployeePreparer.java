package pl.przygudzki.hourly.employee;

public class EmployeePreparer {

	public CreateEmployeeCommand validCreateEmployeeCommand() {
		CreateEmployeeCommand command = new CreateEmployeeCommand();
		command.setPosition(new Position("Position"));
		command.setFirstName("John");
		command.setLastName("Doe");
		return command;
	}

}
