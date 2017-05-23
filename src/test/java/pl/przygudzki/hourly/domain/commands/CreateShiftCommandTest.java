package pl.przygudzki.hourly.domain.commands;

import org.junit.Before;
import org.junit.Test;
import pl.przygudzki.hourly.domain.commands.Validatable.ValidationErrors;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.przygudzki.hourly.domain.commands.Validatable.REQUIRED_FIELD;

public class CreateShiftCommandTest {

	private CreateShiftCommand command;
	private ValidationErrors errors;

	@Before
	public void setUp() throws Exception {
		command = prepareCreateShiftCommand();
		errors = new ValidationErrors();
	}

	@Test
	public void shouldValidateCorrectCommand() {
		command.validate(errors);
		assertThat(errors.isValid()).isTrue();
	}

	@Test
	public void shouldInvalidateNullStartDate() {
		command.setStartDate(null);
		command.validate(errors);
		assertThat(errors.isValid()).isFalse();
		assertThat(errors.getErrors()).containsKey("startDate");
		assertThat(errors.getErrors().get("startDate")).contains(REQUIRED_FIELD);
	}

	@Test
	public void shouldInvalidateNullEndDate() {
		command.setEndDate(null);
		command.validate(errors);
		assertThat(errors.isValid()).isFalse();
		assertThat(errors.getErrors()).containsKey("endDate");
		assertThat(errors.getErrors().get("endDate")).contains(REQUIRED_FIELD);
	}

	@Test
	public void shouldInvalidateStartDateAfterEndDate() {
		command.setStartDate(LocalDateTime.of(2017, 7, 1, 16, 0));
		command.setEndDate(LocalDateTime.of(2017, 7, 1, 12, 0));
		command.validate(errors);
		assertThat(errors.isValid()).isFalse();
		assertThat(errors.getErrors()).containsKey("startDate");
		assertThat(errors.getErrors()).containsKey("endDate");
		assertThat(errors.getErrors().get("startDate")).contains("must be before endDate");
		assertThat(errors.getErrors().get("endDate")).contains("must be after startDate");
	}

	private CreateShiftCommand prepareCreateShiftCommand() {
		CreateShiftCommand command = new CreateShiftCommand();
		command.setStartDate(LocalDateTime.of(2017, 7, 1, 12, 0));
		command.setEndDate(LocalDateTime.of(2017, 7, 1, 16, 0));
		return command;
	}

}