package pl.przygudzki.hourly.position.dto;

import lombok.Getter;
import lombok.Setter;
import pl.przygudzki.hourly.position.PositionId;

@Getter
@Setter
public class PositionDto {

	private PositionId id;
	private String title;
	private String status;

}
