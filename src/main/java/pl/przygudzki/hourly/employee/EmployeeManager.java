package pl.przygudzki.hourly.employee;

import pl.przygudzki.hourly.employee.dto.AddEmployeeCommand;
import pl.przygudzki.hourly.employee.dto.AddPositionCommand;
import pl.przygudzki.hourly.employee.dto.EmployeeDto;

import java.util.Collection;

public interface EmployeeManager {

	void addEmployee(AddEmployeeCommand cmd);

	Collection<EmployeeDto> listEmployees();

	void addPosition(AddPositionCommand command);

	Collection<Position> listPositions();

}
