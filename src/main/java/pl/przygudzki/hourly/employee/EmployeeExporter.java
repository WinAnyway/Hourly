package pl.przygudzki.hourly.employee;

interface EmployeeExporter {

	void exportId(EmployeeId id);

	void exportFirstName(String firstName);

	void exportLastName(String lastName);

	void exportPosition(Position position);

}
