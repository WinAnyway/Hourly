package pl.przygudzki.hourly.position;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class PositionId {

	private static long idSequenceCounter = 0;

	private Long id;

	static PositionId generate() {
		return new PositionId(++idSequenceCounter);
	}

	static PositionId of(Long id) {
		return new PositionId(id);
	}

	Long getId() {
		return id;
	}

}
