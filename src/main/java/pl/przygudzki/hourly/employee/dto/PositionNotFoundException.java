package pl.przygudzki.hourly.employee.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PositionNotFoundException extends RuntimeException {

	private final String title;

}
