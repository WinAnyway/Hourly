package pl.przygudzki.hourly.position;

import java.util.*;

class InMemoryPositionRepository implements PositionRepository {

	private HashMap<PositionId, Position> positions = new HashMap<>();

	@Override
	public void put(Position position) {
		positions.put(position.getId(), position);
	}

	@Override
	public Optional<Position> get(PositionId id) {
		return Optional.ofNullable(positions.get(id));
	}

	@Override
	public List<Position> getAll() {
		return new LinkedList<>(positions.values());
	}

}
