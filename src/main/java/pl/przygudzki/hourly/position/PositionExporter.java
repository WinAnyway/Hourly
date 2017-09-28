package pl.przygudzki.hourly.position;

interface PositionExporter {

	void exportId(PositionId id);

	void exportTitle(String title);

	void exportStatus(PositionStatus status);

}
