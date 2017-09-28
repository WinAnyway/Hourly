package pl.przygudzki.hourly.position;

import org.junit.Before;
import org.junit.Test;
import pl.przygudzki.hourly.commons.commands.Validatable;
import pl.przygudzki.hourly.position.dto.AddPositionCommand;

import static pl.przygudzki.hourly.commons.commands.ValidationErrorsAssertion.assertThat;

public class AddPositionCommandTest {

	private static final String REQUIRED_FIELD = "is a required field and can't be empty";

	private PositionCommandPreparer given = new PositionCommandPreparer();

	private AddPositionCommand command;
	private Validatable.ValidationErrors errors;

	@Before
	public void setUp() throws Exception {
		command = given.validAddPositionCommand();
		errors = new Validatable.ValidationErrors();
	}

	@Test
	public void shouldValidateCorrectCommand() {
		command.validate(errors);

		assertThat(errors).isValid();
	}

	@Test
	public void shouldInvalidateWhenTitleIsNull() {
		command.setTitle(null);

		command.validate(errors);

		assertThat(errors).hasExactly(1).errors("title", REQUIRED_FIELD);
	}

	@Test
	public void shouldInvalidateWhenTitleIsEmpty() {
		command.setTitle("");

		command.validate(errors);

		assertThat(errors).hasExactly(1).errors("title", REQUIRED_FIELD);
	}

}
