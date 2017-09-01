package pl.przygudzki.hourly.shift;

import lombok.Getter;
import lombok.Setter;
import pl.przygudzki.hourly.commands.Validatable;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateShiftCommand implements Validatable {

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
