package pl.przygudzki.hourly.employee;

interface EmployeeExporter {

	void exportId(EmployeeId id);

	void exportPosition(Position position);

	void exportFirstName(String firstName);

	void exportLastName(String lastName);

}
