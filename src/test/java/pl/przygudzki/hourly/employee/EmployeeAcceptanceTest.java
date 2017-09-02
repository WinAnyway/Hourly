package pl.przygudzki.hourly.employee;

import org.junit.Test;
import pl.przygudzki.hourly.commons.commands.InvalidCommandException;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class EmployeeAcceptanceTest {

	private EmployeePreparer given = new EmployeePreparer();
	private EmployeeManager employeeManager = new EmployeeConfiguration().employeeManager();

	@Test
	public void shouldAddEmployee() {
		// given a valid AddEmployeeCommand
		AddEmployeeCommand command = given.validCreateEmployeeCommand();

		// when we add an employee
		employeeManager.addEmployee(command);

		// then system has employee
		Set<EmployeeDto> show = employeeManager.show();
		assertThat(show.size()).isEqualTo(1);
	}

	@Test
	public void shouldNotAddEmployeeWithoutData() {
		// given an empty command
		AddEmployeeCommand command = new AddEmployeeCommand();

		// when we try to add an employee
		Throwable thrown = catchThrowable(() -> employeeManager.addEmployee(command));

		// then system throws exception
		assertThat(thrown).isInstanceOf(InvalidCommandException.class);
	}

	@Test
	public void shouldNotAddEmployeeWithInvalidData() {
		// given a command with invalid data
		AddEmployeeCommand command = given.validCreateEmployeeCommand();
		command.setFirstName("");
		command.setLastName("");
		command.setPosition(null);

		Throwable thrown = catchThrowable(() -> employeeManager.addEmployee(command));

		// then system throws exception
		assertThat(thrown).isInstanceOf(InvalidCommandException.class);
	}

}
