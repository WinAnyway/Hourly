package pl.przygudzki.hourly.employee.dto;

import lombok.Getter;
import lombok.Setter;
import pl.przygudzki.hourly.employee.EmployeeId;
import pl.przygudzki.hourly.position.dto.PositionDto;

@Getter
@Setter
public class EmployeeDto {

	private EmployeeId id;
	private String status;
	private PositionDto position;
	private String firstName;
	private String lastName;

}
