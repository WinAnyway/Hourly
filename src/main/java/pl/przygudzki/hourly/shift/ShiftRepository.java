package pl.przygudzki.hourly.shift;

import java.util.Collection;
import java.util.Optional;

interface ShiftRepository {

	void put(Shift shift);

	Optional<Shift> get(ShiftId shiftId);

	Collection<Shift> getAll();

}
