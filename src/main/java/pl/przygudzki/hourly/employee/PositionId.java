package pl.przygudzki.hourly.employee;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class PositionId {

	private static long idSequenceCounter = 0;

	private Long id;

	static PositionId generate() {
		return new PositionId(++idSequenceCounter);
	}

	Long getId() {
		return id;
	}

	static PositionId of(Long id) {
		return new PositionId(id);
	}

}
