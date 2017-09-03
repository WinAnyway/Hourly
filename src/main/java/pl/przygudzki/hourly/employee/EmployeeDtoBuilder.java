package pl.przygudzki.hourly.employee;

import pl.przygudzki.hourly.employee.dto.EmployeeDto;

class EmployeeDtoBuilder implements EmployeeExporter {

	private EmployeeDto dto = new EmployeeDto();

	EmployeeDto build() {
		EmployeeDto result = dto;
		dto = new EmployeeDto();
		return result;
	}

	@Override
	public void exportId(EmployeeId id) {
		dto.setId(id.getId());
	}

	@Override
	public void exportFirstName(String firstName) {
		dto.setFirstName(firstName);
	}

	@Override
	public void exportLastName(String lastName) {
		dto.setLastName(lastName);
	}

	@Override
	public void exportPosition(Position position) {
		dto.setPosition(position.getTitle());
	}

}
