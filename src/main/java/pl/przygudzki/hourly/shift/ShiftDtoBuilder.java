package pl.przygudzki.hourly.shift;

import pl.przygudzki.hourly.shift.dto.ShiftDto;

import java.time.LocalDateTime;

class ShiftDtoBuilder implements ShiftExporter {

	private ShiftDto dto = new ShiftDto();

	ShiftDto build() {
		ShiftDto result = dto;
		dto = new ShiftDto();
		return result;
	}

	@Override
	public void exportId(ShiftId id) {
		dto.setId(id);
	}

	@Override
	public void exportStatus(ShiftStatus status) {
		dto.setStatus(status.toString());
	}

	@Override
	public void exportStartDate(LocalDateTime startDate) {
		dto.setStartDate(startDate);
	}

	@Override
	public void exportEndDate(LocalDateTime endDate) {
		dto.setEndDate(endDate);
	}

}
