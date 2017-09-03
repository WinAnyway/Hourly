package pl.przygudzki.hourly.employee;

import pl.przygudzki.hourly.employee.dto.AddPositionCommand;
import pl.przygudzki.hourly.employee.dto.EditPositionCommand;
import pl.przygudzki.hourly.employee.dto.PositionDto;

import java.util.Collection;

public interface PositionManager {

	void addPosition(AddPositionCommand command);

	Position getPositionOrThrow(Long positionId);

	void editPosition(EditPositionCommand command, Long id);

	void removePosition(Long id);

	Collection<PositionDto> listPositions();

}
