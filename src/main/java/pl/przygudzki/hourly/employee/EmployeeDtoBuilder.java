package pl.przygudzki.hourly.employee;

import pl.przygudzki.hourly.employee.dto.EmployeeDto;
import pl.przygudzki.hourly.position.dto.PositionDto;
import pl.przygudzki.hourly.position.PositionId;
import pl.przygudzki.hourly.position.PositionManager;

class EmployeeDtoBuilder implements EmployeeExporter {

	private PositionManager positionManager;

	private EmployeeDto dto = new EmployeeDto();

	EmployeeDtoBuilder(PositionManager positionManager) {
		this.positionManager = positionManager;
	}

	EmployeeDto build() {
		EmployeeDto result = dto;
		dto = new EmployeeDto();
		return result;
	}

	@Override
	public void exportId(EmployeeId id) {
		dto.setId(id);
	}

	@Override
	public void exportStatus(EmployeeStatus status) {
		dto.setStatus(status.toString());
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
	public void exportPositionId(PositionId positionId) {
		PositionDto positionDto = positionManager.getPosition(positionId);
		dto.setPosition(positionDto);
	}

}
