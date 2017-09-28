package pl.przygudzki.hourly.position;

public class PositionConfiguration {

	public PositionManager positionManager() {
		PositionRepository positionRepository = new InMemoryPositionRepository();
		return new StandardPositionManager(positionRepository);
	}

}
