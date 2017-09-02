package pl.przygudzki.hourly.employee;

public class EmployeePreparer {

	public AddEmployeeCommand validCreateEmployeeCommand() {
		AddEmployeeCommand command = new AddEmployeeCommand();
		command.setPosition(new Position("Position"));
		command.setFirstName("John");
		command.setLastName("Doe");
		return command;
	}

}
