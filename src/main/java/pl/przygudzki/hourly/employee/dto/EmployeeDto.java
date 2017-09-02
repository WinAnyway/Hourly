package pl.przygudzki.hourly.employee.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDto {

	private Long id;
	private String position;
	private String firstName;
	private String lastName;

}
