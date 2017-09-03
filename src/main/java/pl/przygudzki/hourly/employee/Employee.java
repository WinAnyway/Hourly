package pl.przygudzki.hourly.employee;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.przygudzki.hourly.employee.dto.AddEmployeeCommand;

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

	static Employee create(AddEmployeeCommand command, Position position) {
		return new Employee(
				EmployeeId.generate(),
				position,
				command.getFirstName(),
				command.getLastName()
		);
	}

	void export(EmployeeDtoBuilder exporter) {
		exporter.exportId(id);
		exporter.exportPosition(position);
		exporter.exportFirstName(firstName);
		exporter.exportLastName(lastName);
	}

	EmployeeId getId() {
		return id;
	}

}
