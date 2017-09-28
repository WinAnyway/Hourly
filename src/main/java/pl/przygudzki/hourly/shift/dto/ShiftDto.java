package pl.przygudzki.hourly.shift.dto;

import lombok.Getter;
import lombok.Setter;
import pl.przygudzki.hourly.shift.ShiftId;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShiftDto {

	private ShiftId id;
	private String status;
	private LocalDateTime startDate;
	private LocalDateTime endDate;

}
