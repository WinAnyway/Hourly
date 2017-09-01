package pl.przygudzki.hourly.shift;

import org.junit.Before;
import org.junit.Test;
import pl.przygudzki.hourly.commands.Validatable.ValidationErrors;

import java.time.LocalDateTime;

import static pl.przygudzki.hourly.commands.ValidationErrorsAssertion.assertThat;

public class CreateShiftCommandTest {

	private static final String REQUIRED_FIELD = "is a required field and can't be empty";

	private ShiftPreparer given = new ShiftPreparer();

	private CreateShiftCommand command;
	private ValidationErrors errors;

	@Before
	public void setUp() throws Exception {
		command = given.validCreateShiftCommand();
		errors = new ValidationErrors();
	}

	@Test
	public void shouldValidateCorrectCommand() {
		command.validate(errors);

		assertThat(errors).isValid();
	}

	@Test
	public void shouldInvalidateNullStartDate() {
		command.setStartDate(null);

		command.validate(errors);

		assertThat(errors).isNotValid().hasExactly(1).errors("startDate", REQUIRED_FIELD);
	}

	@Test
	public void shouldInvalidateNullEndDate() {
		command.setEndDate(null);

		command.validate(errors);

		assertThat(errors).isNotValid().hasExactly(1).errors("endDate", REQUIRED_FIELD);
	}

	@Test
	public void shouldInvalidateStartDateAfterEndDate() {
		command.setStartDate(LocalDateTime.of(2017, 7, 1, 16, 0));
		command.setEndDate(LocalDateTime.of(2017, 7, 1, 12, 0));

		command.validate(errors);

		assertThat(errors).isNotValid()
				.hasExactly(2)
				.hasError("startDate", "must be before endDate")
				.hasError("endDate", "must be after startDate");
	}

	private CreateShiftCommand prepareValidCreateShiftCommand() {
		CreateShiftCommand command = new CreateShiftCommand();
		command.setStartDate(LocalDateTime.of(2017, 7, 1, 12, 0));
		command.setEndDate(LocalDateTime.of(2017, 7, 1, 16, 0));
		return command;
	}

}
