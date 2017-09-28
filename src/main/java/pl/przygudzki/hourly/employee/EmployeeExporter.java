package pl.przygudzki.hourly.employee;

import pl.przygudzki.hourly.position.PositionId;

interface EmployeeExporter {

	void exportId(EmployeeId id);

	void exportStatus(EmployeeStatus status);

	void exportFirstName(String firstName);

	void exportLastName(String lastName);

	void exportPositionId(PositionId position);

}
