package pl.przygudzki.hourly.employee;

class Position {

	private String title;

	private Position(String position) {
		this.title = position;
	}

	String getTitle() {
		return title;
	}

	static Position create(AddPositionCommand command) {
		return new Position(command.getTitle());
	}

}
