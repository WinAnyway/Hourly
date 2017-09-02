package pl.przygudzki.hourly.employee;

import org.junit.Before;
import org.junit.Test;
import pl.przygudzki.hourly.commons.commands.Validatable;

import static pl.przygudzki.hourly.commons.commands.ValidationErrorsAssertion.assertThat;

public class AddEmployeeCommandTest {

	private static final String REQUIRED_FIELD = "is a required field and can't be empty";

	private EmployeePreparer given = new EmployeePreparer();

	private AddEmployeeCommand command;
	private Validatable.ValidationErrors errors;

	@Before
	public void setUp() throws Exception {
		command = given.validAddEmployeeCommand();
		errors = new Validatable.ValidationErrors();
	}

	@Test
	public void shouldValidateCorrectCommand() {
		command.validate(errors);

		assertThat(errors).isValid();
	}

	@Test
	public void shouldInvalidateWhenPositionIsNull() {
		command.setPositionTitle(null);

		command.validate(errors);

		assertThat(errors).hasExactly(1).errors("positionTitle", REQUIRED_FIELD);
	}

	@Test
	public void shouldInvalidateWhenPositionNameIsEmpty() {
		command.setPositionTitle("");

		command.validate(errors);

		assertThat(errors).hasExactly(1).errors("positionTitle", REQUIRED_FIELD);
	}

	@Test
	public void shouldInvalidateWhenFirstNameIsNull() {
		command.setFirstName(null);

		command.validate(errors);

		assertThat(errors).hasExactly(1).errors("firstName", REQUIRED_FIELD);
	}

	@Test
	public void shouldInvalidateWhenFirstNameIsEmpty() {
		command.setFirstName("");

		command.validate(errors);

		assertThat(errors).hasExactly(1).errors("firstName", REQUIRED_FIELD);
	}

	@Test
	public void shouldInvalidateWhenLastNameIsNull() {
		command.setLastName(null);

		command.validate(errors);

		assertThat(errors).hasExactly(1).errors("lastName", REQUIRED_FIELD);
	}

	@Test
	public void shouldInvalidateWhenLastNameIsEmpty() {
		command.setLastName("");

		command.validate(errors);

		assertThat(errors).hasExactly(1).errors("lastName", REQUIRED_FIELD);
	}

}
