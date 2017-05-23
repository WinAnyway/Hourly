package pl.przygudzki.hourly.domain.commands;

import java.time.LocalDateTime;

public class CreateShiftCommand implements Validatable {

	private LocalDateTime startDate;
	private LocalDateTime endDate;

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	@Override
	public void validate(ValidationErrors errors) {
		validateDates(errors);
	}

	private void validateDates(ValidationErrors errors) {
		validateStartDate(errors);
		validateEndDate(errors);
		if (startDate != null && endDate != null && !startDate.isBefore(endDate)) {
			errors.add("startDate", "must be before endDate");
			errors.add("endDate", "must be after startDate");
		}
	}

	private void validateEndDate(ValidationErrors errors) {
		if (endDate == null)
			errors.add("endDate", REQUIRED_FIELD);
	}

	private void validateStartDate(ValidationErrors errors) {
		if (startDate == null)
			errors.add("startDate", REQUIRED_FIELD);
	}

}
