package pl.przygudzki.hourly.employee;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PositionId {

	private static long idSequenceCounter = 0;

	private Long id;

	static PositionId generate() {
		return new PositionId(++idSequenceCounter);
	}

	Long getId() {
		return id;
	}

}
