package pl.przygudzki.hourly.shift;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
class ShiftId implements Serializable {

	private static long idSequencePointer = 0;

	private Long id;

	private ShiftId(long id) {
		this.id = id;
	}

	static ShiftId generate() {
		return new ShiftId(++idSequencePointer);
	}

}
