package pl.przygudzki.hourly.shift.dto;

import lombok.Getter;
import lombok.Setter;
import pl.przygudzki.hourly.commons.commands.Validatable;

import java.time.LocalDateTime;

@Getter
@Setter
public class EditShiftCommand implements Validatable {

	private LocalDateTime startDate;
	private LocalDateTime endDate;

	@Override
	public void validate(ValidationErrors errors) {
		validateDates(errors);
	}

	private void validateDates(ValidationErrors errors) {
		validateStartDate(errors);
		validateEndDate(errors);
		if (startDate != null && endDate != null && !startDate.isBefore(endDate)) {
			errors.add("startDate", "must occur before endDate");
			errors.add("endDate", "must occur after startDate");
		}
	}

	private void validateStartDate(ValidationErrors errors) {
		if (startDate == null)
			errors.add("startDate", REQUIRED_FIELD);
	}

	private void validateEndDate(ValidationErrors errors) {
		if (endDate == null)
			errors.add("endDate", REQUIRED_FIELD);
	}

}
