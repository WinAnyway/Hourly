package pl.przygudzki.hourly.domain.shift;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class ShiftId implements Serializable {

	private UUID id;

	private ShiftId(UUID id) {
		this.id = id;
	}

	private ShiftId() {
	}

	public static ShiftId generate() {
		return new ShiftId(UUID.randomUUID());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ShiftId shiftId = (ShiftId) o;

		return id != null ? id.equals(shiftId.id) : shiftId.id == null;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

}
