package pl.przygudzki.hourly.employee;

import java.util.Collection;

public interface EmployeeManager {

	void addEmployee(AddEmployeeCommand cmd);

	Collection<EmployeeDto> showEmployees();

	void addPosition(AddPositionCommand command);

	Collection<Position> showPositions();

}
