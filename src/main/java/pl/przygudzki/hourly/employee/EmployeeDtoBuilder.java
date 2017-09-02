package pl.przygudzki.hourly.employee;

class EmployeeDtoBuilder implements EmployeeExporter {

	private EmployeeDto dto = new EmployeeDto();

	EmployeeDto build() {
		EmployeeDto result = dto;
		dto = new EmployeeDto();
		return result;
	}

	void exportId(EmployeeId id) {
		dto.setId(id);
	}

	void exportPosition(Position position) {
		dto.setPosition(position.getTitle());
	}

	void exportFirstName(String firstName) {
		dto.setFirstName(firstName);
	}

	void exportLastName(String lastName) {
		dto.setLastName(lastName);
	}

}
