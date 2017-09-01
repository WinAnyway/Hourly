package pl.przygudzki.hourly.employee;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Employee {

	private EmployeeId id;
	private Position position;
	private String firstName;
	private String lastName;

	private Employee(EmployeeId id, Position position, String firstName, String lastName) {
		this.id = id;
		this.position = position;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public static Employee create(CreateEmployeeCommand command) {
		return new Employee(EmployeeId.generate(), command.getPosition(), command.getFirstName(), command.getLastName());
	}

}
