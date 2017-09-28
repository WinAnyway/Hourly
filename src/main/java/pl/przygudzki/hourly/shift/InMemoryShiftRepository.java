package pl.przygudzki.hourly.shift;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class InMemoryShiftRepository implements ShiftRepository {

	private Map<ShiftId, Shift> shifts = new HashMap<>();

	@Override
	public void put(Shift shift) {
		shifts.put(shift.getId(), shift);
	}

	@Override
	public Optional<Shift> get(ShiftId shiftId) {
		return Optional.ofNullable(shifts.get(shiftId));
	}

	@Override
	public Collection<Shift> getAll() {
		return shifts.values();
	}

}
