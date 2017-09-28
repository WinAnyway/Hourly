package pl.przygudzki.hourly.position;

import lombok.AllArgsConstructor;
import pl.przygudzki.hourly.position.dto.AddPositionCommand;
import pl.przygudzki.hourly.position.dto.PositionDto;

import java.util.Collection;

@AllArgsConstructor
public class PositionPreparer {

	private final PositionManager positionManager;

	private final PositionCommandPreparer given = new PositionCommandPreparer();

	public PositionDto newPositionIsAdded() {
		return newPositionIsAdded(given.validAddPositionCommand());
	}

	PositionDto newPositionIsAdded(AddPositionCommand command) {
		Collection<PositionDto> beforePositions = positionManager.listPositions();
		positionManager.addPosition(command);
		return positionManager.listPositions().stream()
				.filter(positionDto -> !beforePositions.contains(positionDto))
				.findAny().orElseThrow(IllegalStateException::new);
	}

	PositionId newRemovedPosition() {
		PositionId positionId = newPositionIsAdded().getId();
		positionManager.removePosition(positionId);
		return positionId;
	}

}
