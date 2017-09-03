package pl.przygudzki.hourly.employee;

import pl.przygudzki.hourly.employee.dto.EmployeeDto;
import pl.przygudzki.hourly.employee.dto.PositionDto;

class EmployeeDtoBuilder implements EmployeeExporter {

	private EmployeeDto dto = new EmployeeDto();
	private final PositionDtoBuilder builder = new PositionDtoBuilder();

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
		position.export(builder);
		PositionDto positionDto = builder.build();
		dto.setPosition(positionDto.getTitle());
	}

}
