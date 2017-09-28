package pl.przygudzki.hourly.employee;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.przygudzki.hourly.employee.dto.AddEmployeeCommand;
import pl.przygudzki.hourly.employee.dto.EditEmployeeCommand;
import pl.przygudzki.hourly.position.PositionId;

import static pl.przygudzki.hourly.employee.EmployeeStatus.AVAILABLE;
import static pl.przygudzki.hourly.employee.EmployeeStatus.REMOVED;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class Employee {

	private EmployeeId id;
	private EmployeeStatus status;
	private PositionId positionId;
	private String firstName;
	private String lastName;

	private Employee(EmployeeId id, PositionId positionId, String firstName, String lastName) {
		this.id = id;
		this.status = AVAILABLE;
		this.positionId = positionId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	static Employee create(AddEmployeeCommand command) {
		return new Employee(
				EmployeeId.generate(),
				command.getPositionId(),
				command.getFirstName(),
				command.getLastName()
		);
	}

	void edit(EditEmployeeCommand editCommand) {
		this.positionId = editCommand.getPositionId();
		this.firstName = editCommand.getFirstName();
		this.lastName = editCommand.getLastName();
	}

	void remove() {
		this.status = REMOVED;
	}

	boolean isAvailable() {
		return status == AVAILABLE;
	}

	void export(EmployeeDtoBuilder exporter) {
		exporter.exportId(id);
		exporter.exportStatus(status);
		exporter.exportPositionId(positionId);
		exporter.exportFirstName(firstName);
		exporter.exportLastName(lastName);
	}

	EmployeeId getId() {
		return id;
	}

}
