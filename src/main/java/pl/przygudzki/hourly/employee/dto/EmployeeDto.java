package pl.przygudzki.hourly.employee.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDto {

	private Long id;

	// TODO EmployeeDto should not have a var position: String, but rather a var position: Position
	private String position;

	private String firstName;
	private String lastName;

}
