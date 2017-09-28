package pl.przygudzki.hourly.position.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.przygudzki.hourly.position.PositionId;

@AllArgsConstructor
@Getter
public class PositionNotAvailableException extends RuntimeException {

	private final PositionId positionId;

}
