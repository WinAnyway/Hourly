package pl.przygudzki.hourly.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.przygudzki.hourly.employee.EmployeeId;

@AllArgsConstructor
@Getter
public class EmployeeNotFoundException extends RuntimeException {

	private final EmployeeId employeeId;

}
