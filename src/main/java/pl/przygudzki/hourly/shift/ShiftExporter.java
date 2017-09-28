package pl.przygudzki.hourly.shift;

import java.time.LocalDateTime;

interface ShiftExporter {

	void exportId(ShiftId id);

	void exportStatus(ShiftStatus status);

	void exportStartDate(LocalDateTime startDate);

	void exportEndDate(LocalDateTime endDate);

}
