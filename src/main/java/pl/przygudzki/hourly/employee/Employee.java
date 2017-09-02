package pl.przygudzki.hourly.employee;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class Employee {

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

	public static Employee create(AddEmployeeCommand command) {
		return new Employee(EmployeeId.generate(), command.getPosition(), command.getFirstName(), command.getLastName());
	}

	void export(EmployeeDtoBuilder exporter) {
		exporter.exportId(id);
		exporter.exportPosition(position);
		exporter.exportFirstName(firstName);
		exporter.exportLastName(lastName);
	}

}
