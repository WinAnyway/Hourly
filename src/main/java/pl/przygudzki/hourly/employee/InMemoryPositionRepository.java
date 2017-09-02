package pl.przygudzki.hourly.employee;

import java.util.*;

class InMemoryPositionRepository implements PositionRepository {

	private HashMap<String, Position> positions = new HashMap<>();

	@Override
	public void put(Position position) {
		positions.put(position.getTitle(), position);
	}

	@Override
	public List<Position> getAll() {
		return new LinkedList<>(positions.values());
	}

	@Override
	public Optional<Position> get(String title) {
		return Optional.ofNullable(positions.get(title));
	}

}
