package pl.przygudzki.hourly.position.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.przygudzki.hourly.position.PositionId;

@RequiredArgsConstructor
@Getter
public class PositionNotFoundException extends RuntimeException {

	private final PositionId positionId;

}
