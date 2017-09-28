package pl.przygudzki.hourly.shift;

import org.junit.Before;
import org.junit.Test;
import pl.przygudzki.hourly.commons.commands.Validatable;
import pl.przygudzki.hourly.shift.dto.AddShiftCommand;

import java.time.LocalDateTime;

import static pl.przygudzki.hourly.commons.commands.ValidationErrorsAssertion.assertThat;

public class EditShiftCommandTest {

	private static final LocalDateTime LATER_DATE_TIME = LocalDateTime.of(2017, 7, 1, 16, 0);
	private static final LocalDateTime EARLIER_DATE_TIME = LocalDateTime.of(2017, 7, 1, 12, 0);
	private static final String REQUIRED_FIELD = "is a required field and can't be empty";
	private static final String START_DATE_FIELD = "startDate";
	private static final String END_DATE_FIELD = "endDate";

	private CommandPreparer given = new CommandPreparer();

	private AddShiftCommand command;
	private Validatable.ValidationErrors errors;

	@Before
	public void setUp() throws Exception {
		command = given.validAddShiftCommand();
		errors = new Validatable.ValidationErrors();
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

		assertThat(errors).isNotValid().hasExactly(1).errors(START_DATE_FIELD, REQUIRED_FIELD);
	}

	@Test
	public void shouldInvalidateNullEndDate() {
		command.setEndDate(null);

		command.validate(errors);

		assertThat(errors).isNotValid().hasExactly(1).errors(END_DATE_FIELD, REQUIRED_FIELD);
	}

	@Test
	public void shouldInvalidateStartDateAfterEndDate() {
		command.setStartDate(LATER_DATE_TIME);
		command.setEndDate(EARLIER_DATE_TIME);

		command.validate(errors);

		assertThat(errors).isNotValid()
				.hasExactly(2)
				.hasError(START_DATE_FIELD, "must occur before endDate")
				.hasError(END_DATE_FIELD, "must occur after startDate");
	}

}