package pl.przygudzki.hourly.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EmployeeNotFoundException extends RuntimeException {

	private final Long employeeId;

}
