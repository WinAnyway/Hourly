package pl.przygudzki.hourly.position;

import pl.przygudzki.hourly.position.dto.PositionDto;

class PositionDtoBuilder implements PositionExporter {

	private PositionDto dto = new PositionDto();

	PositionDto build() {
		PositionDto result = dto;
		dto = new PositionDto();
		return result;
	}

	@Override
	public void exportId(PositionId positionId) {
		dto.setId(positionId);
	}

	@Override
	public void exportTitle(String title) {
		dto.setTitle(title);
	}

	@Override
	public void exportStatus(PositionStatus status) {
		dto.setStatus(status.toString());
	}

}
