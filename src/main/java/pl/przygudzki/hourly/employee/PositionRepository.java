package pl.przygudzki.hourly.employee;

import java.util.List;
import java.util.Optional;

interface PositionRepository {

	void put(Position position);

	List<Position> getAll();

	Optional<Position> get(String title);

}
