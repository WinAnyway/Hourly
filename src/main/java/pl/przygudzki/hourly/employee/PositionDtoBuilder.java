package pl.przygudzki.hourly.employee;

import pl.przygudzki.hourly.employee.dto.PositionDto;

class PositionDtoBuilder implements PositionExporter {

	private PositionDto dto = new PositionDto();

	PositionDto build() {
		PositionDto result = dto;
		dto = new PositionDto();
		return result;
	}

	@Override
	public void exportId(PositionId id) {
		dto.setId(id.getId());
	}

	@Override
	public void exportTitle(String title) {
		dto.setTitle(title);
	}

}
