package pl.przygudzki.hourly.shift;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class ShiftId {

	private static long idSequencePointer = 0;

	private Long id;

	private ShiftId(long id) {
		this.id = id;
	}

	static ShiftId generate() {
		return new ShiftId(++idSequencePointer);
	}

	static ShiftId of(Long id) {
		return new ShiftId(id);
	}

}
