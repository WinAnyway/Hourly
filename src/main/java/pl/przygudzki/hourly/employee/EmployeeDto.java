package pl.przygudzki.hourly.employee;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDto {
	private EmployeeId id;
	private String position;
	private String firstName;
	private String lastName;

	void setId(EmployeeId id) {
		this.id = id;
	}

	void setPosition(String position) {
		this.position = position;
	}

	void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
