package pl.przygudzki.hourly.employee;

import org.junit.Before;
import org.junit.Test;
import pl.przygudzki.hourly.commons.commands.Validatable;
import pl.przygudzki.hourly.employee.dto.EditEmployeeCommand;

import static pl.przygudzki.hourly.commons.commands.ValidationErrorsAssertion.assertThat;

public class EditEmployeeCommandTest {

	private static final String REQUIRED_FIELD = "is a required field and can't be empty";
	private static final String POSITION_ID_FIELD = "positionId";
	private static final String FIRST_NAME_FIELD = "firstName";
	private static final String LAST_NAME_FIELD = "lastName";

	private EmployeeCommandPreparer given = new EmployeeCommandPreparer();

	private EditEmployeeCommand command;
	private Validatable.ValidationErrors errors;

	@Before
	public void setUp() throws Exception {
		command = given.validEditEmployeeCommand();
		errors = new Validatable.ValidationErrors();
	}

	@Test
	public void shouldValidateCorrectCommand() {
		command.validate(errors);

		assertThat(errors).isValid();
	}

	@Test
	public void shouldInvalidateWhenPositionIsNull() {
		command.setPositionId(null);

		command.validate(errors);

		assertThat(errors).hasExactly(1).errors(POSITION_ID_FIELD, REQUIRED_FIELD);
	}

	@Test
	public void shouldInvalidateWhenFirstNameIsNull() {
		command.setFirstName(null);

		command.validate(errors);

		assertThat(errors).hasExactly(1).errors(FIRST_NAME_FIELD, REQUIRED_FIELD);
	}

	@Test
	public void shouldInvalidateWhenFirstNameIsEmpty() {
		command.setFirstName("");

		command.validate(errors);

		assertThat(errors).hasExactly(1).errors(FIRST_NAME_FIELD, REQUIRED_FIELD);
	}

	@Test
	public void shouldInvalidateWhenLastNameIsNull() {
		command.setLastName(null);

		command.validate(errors);

		assertThat(errors).hasExactly(1).errors(LAST_NAME_FIELD, REQUIRED_FIELD);
	}

	@Test
	public void shouldInvalidateWhenLastNameIsEmpty() {
		command.setLastName("");

		command.validate(errors);

		assertThat(errors).hasExactly(1).errors(LAST_NAME_FIELD, REQUIRED_FIELD);
	}

}