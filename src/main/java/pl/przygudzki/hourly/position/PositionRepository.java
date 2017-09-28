package pl.przygudzki.hourly.position;

import java.util.List;
import java.util.Optional;

interface PositionRepository {

	void put(Position position);

	List<Position> getAll();

	Optional<Position> get(PositionId id);

}
