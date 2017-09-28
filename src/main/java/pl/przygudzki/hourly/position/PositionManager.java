package pl.przygudzki.hourly.position;

import pl.przygudzki.hourly.position.dto.AddPositionCommand;
import pl.przygudzki.hourly.position.dto.EditPositionCommand;
import pl.przygudzki.hourly.position.dto.PositionDto;

import java.util.Collection;

public interface PositionManager {

	void addPosition(AddPositionCommand command);

	PositionDto getPosition(PositionId positionId);

	Collection<PositionDto> listPositions();

	void editPosition(PositionId command, EditPositionCommand positionId);

	void removePosition(PositionId id);

	boolean isAvailable(PositionId positionId);

}
