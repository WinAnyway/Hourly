package pl.przygudzki.hourly.shift.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.przygudzki.hourly.shift.ShiftId;

@AllArgsConstructor
@Getter
public class ShiftNotFoundException extends RuntimeException {

	private final ShiftId shiftId;

}
