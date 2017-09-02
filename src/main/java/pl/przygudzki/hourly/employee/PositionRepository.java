package pl.przygudzki.hourly.employee;

import java.util.List;

interface PositionRepository {

	void put(Position position);

	List<Position> getAll();

	Position get(String title);

}
