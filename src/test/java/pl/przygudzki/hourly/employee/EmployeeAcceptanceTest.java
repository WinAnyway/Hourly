package pl.przygudzki.hourly.employee;

import org.junit.Test;
import pl.przygudzki.hourly.commons.commands.InvalidCommandException;
import pl.przygudzki.hourly.employee.dto.AddEmployeeCommand;
import pl.przygudzki.hourly.employee.dto.AddPositionCommand;
import pl.przygudzki.hourly.employee.dto.EmployeeDto;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class EmployeeAcceptanceTest {

	private EmployeePreparer given = new EmployeePreparer();
	private EmployeeManager employeeManager = new EmployeeConfiguration().employeeManager();

	@Test
	public void shouldAddPosition() {
		// given a valid AddPositionCommand
		AddPositionCommand command = given.validAddPositionCommand();

		// when we add a positionTitle
		employeeManager.addPosition(command);

		// then system has a positionTitle
		Collection<Position> positions = employeeManager.listPositions();
		assertThat(positions.size()).isEqualTo(1);
	}

	@Test
	public void shouldNotAddPositionWithoutTitle() {
		// given an empty command
		AddPositionCommand command = new AddPositionCommand();

		// when we try to add an employee
		Throwable thrown = catchThrowable(() -> employeeManager.addPosition(command));

		// then system throws exception
		assertThat(thrown).isInstanceOf(InvalidCommandException.class);
	}

	@Test
	public void shouldNotAddPositionWithInvalidData() {
		// given a command with invalid data
		AddPositionCommand command = given.validAddPositionCommand();
		command.setTitle("");

		Throwable thrown = catchThrowable(() -> employeeManager.addPosition(command));

		// then system throws exception
		assertThat(thrown).isInstanceOf(InvalidCommandException.class);
	}

	@Test
	public void shouldListPositions() {
		// given two positions added to the system
		employeeManager.addPosition(given.validAddPositionCommand());
		employeeManager.addPosition(given.anotherValidAddPositionCommand());

		// when we list available positions
		Collection<Position> positions = employeeManager.listPositions();

		// then system returns a list of two positions
		assertThat(positions.size()).isEqualTo(2);
	}

	@Test
	public void shouldAddEmployee() {
		// given that a valid AddEmployeeCommand
		AddEmployeeCommand command = given.validAddEmployeeCommand();
		// and that a suitable position exists in the system
		employeeManager.addPosition(given.validAddPositionCommand());

		// when we add an employee
		employeeManager.addEmployee(command);

		// then system has employee
		Collection<EmployeeDto> employeeDtos = employeeManager.listEmployees();
		assertThat(employeeDtos.size()).isEqualTo(1);
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
		AddEmployeeCommand command = given.validAddEmployeeCommand();
		command.setFirstName("");
		command.setLastName("");
		command.setPositionTitle(null);

		Throwable thrown = catchThrowable(() -> employeeManager.addEmployee(command));

		// then system throws exception
		assertThat(thrown).isInstanceOf(InvalidCommandException.class);
	}

}
