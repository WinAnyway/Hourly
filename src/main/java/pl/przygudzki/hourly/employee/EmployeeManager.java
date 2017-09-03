package pl.przygudzki.hourly.employee;

import pl.przygudzki.hourly.employee.dto.*;

import java.util.Collection;

public interface EmployeeManager {

	void addEmployee(AddEmployeeCommand cmd);

	Collection<EmployeeDto> listEmployees();

	void addPosition(AddPositionCommand command);

	Position getPositionOrThrow(Long positionId);

	Collection<PositionDto> listPositions();

	void editPosition(EditPositionCommand command, Long id);

	void removePosition(Long id);

}
