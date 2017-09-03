package pl.przygudzki.hourly.employee;

import org.junit.Before;
import org.junit.Test;
import pl.przygudzki.hourly.commons.commands.Validatable;
import pl.przygudzki.hourly.employee.dto.EditPositionCommand;

import static pl.przygudzki.hourly.commons.commands.ValidationErrorsAssertion.assertThat;

public class EditPositionCommandTest {

	private static final String REQUIRED_FIELD = "is a required field and can't be empty";

	private EmployeePreparer given = new EmployeePreparer();

	private EditPositionCommand command;
	private Validatable.ValidationErrors errors;

	@Before
	public void setUp() throws Exception {
		command = given.validEditPositionCommand();
		errors = new Validatable.ValidationErrors();
	}

	@Test
	public void shouldValidateCorrectCommand() {
		command.validate(errors);

		assertThat(errors).isValid();
	}

	@Test
	public void shouldInvalidateWhenFirstNameIsNull() {
		command.setTitle(null);

		command.validate(errors);

		assertThat(errors).hasExactly(1).errors("title", REQUIRED_FIELD);
	}

	@Test
	public void shouldInvalidateWhenFirstNameIsEmpty() {
		command.setTitle("");

		command.validate(errors);

		assertThat(errors).hasExactly(1).errors("title", REQUIRED_FIELD);
	}

}