package pl.przygudzki.hourly.api;

import org.junit.Before;
import org.junit.Test;
import pl.przygudzki.hourly.api.implementation.StandardEmployeeManager;
import pl.przygudzki.hourly.domain.Employee;
import pl.przygudzki.hourly.domain.EmployeeId;
import pl.przygudzki.hourly.domain.EmployeeRepository;
import pl.przygudzki.hourly.domain.Position;
import pl.przygudzki.hourly.domain.commands.CreateEmployeeCommand;
import pl.przygudzki.hourly.domain.commands.InvalidCommandException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

public class StandardEmployeeManagerTest {

	private EmployeeRepository employeeRepository;
	private EmployeeManager employeeManager;
	private CreateEmployeeCommand command;

	@Before
	public void setUp() {
		employeeRepository = mock(EmployeeRepository.class);
		employeeManager = new StandardEmployeeManager(employeeRepository);
		command = prepareCreateEmployeeCommand();
	}

	@Test
	public void shouldCreateEmployee() {
		employeeManager.createEmployee(command);
		verify(employeeRepository, times(1)).put(new Employee(command));
	}

	@Test
	public void shouldNotCreateEmployeeWhenEmployeeIdIsNull() {
		command.setEmployeeId(null);
		Throwable thrown = catchThrowable( () -> employeeManager.createEmployee(command) );
		assertThat(thrown).isInstanceOf(InvalidCommandException.class);
	}

	@Test(expected = InvalidCommandException.class)
	public void shouldNotCreateEmployeeWhenPositionIsNull() {
		CreateEmployeeCommand cmd = new CreateEmployeeCommand();
		cmd.setEmployeeId(new EmployeeId(1L));
		cmd.setFirstName("John");
		cmd.setLastName("Doe");

		employeeManager.createEmployee(cmd);
	}

	@Test(expected = InvalidCommandException.class)
	public void shouldNotCreateEmployeeWhenPositionIsEmpty() {
		CreateEmployeeCommand cmd = new CreateEmployeeCommand();
		cmd.setEmployeeId(new EmployeeId(1L));
		cmd.setPosition(new Position(" "));
		cmd.setFirstName("John");
		cmd.setLastName("Doe");

		employeeManager.createEmployee(cmd);
	}

	@Test(expected = InvalidCommandException.class)
	public void shouldNotCreateEmployeeWhenFirstNameIsNull() {
		CreateEmployeeCommand cmd = new CreateEmployeeCommand();
		cmd.setEmployeeId(new EmployeeId(1L));
		cmd.setPosition(new Position("Position"));
		cmd.setLastName("Doe");

		employeeManager.createEmployee(cmd);
	}

	@Test(expected = InvalidCommandException.class)
	public void shouldNotCreateEmployeeWhenFirstNameIsEmpty() {
		CreateEmployeeCommand cmd = new CreateEmployeeCommand();
		cmd.setEmployeeId(new EmployeeId(1L));
		cmd.setPosition(new Position("Position"));
		cmd.setFirstName("");
		cmd.setLastName("Doe");

		employeeManager.createEmployee(cmd);
	}

	@Test(expected = InvalidCommandException.class)
	public void shouldNotCreateEmployeeWhenLastNameIsNull() {
		CreateEmployeeCommand cmd = new CreateEmployeeCommand();
		cmd.setEmployeeId(new EmployeeId(1L));
		cmd.setPosition(new Position("Position"));
		cmd.setFirstName("John");

		employeeManager.createEmployee(cmd);
	}

	@Test(expected = InvalidCommandException.class)
	public void shouldNotCreateEmployeeWhenLastNameIsEmpty() {
		CreateEmployeeCommand cmd = new CreateEmployeeCommand();
		cmd.setEmployeeId(new EmployeeId(1L));
		cmd.setPosition(new Position("Position"));
		cmd.setFirstName("John");
		cmd.setLastName("");

		employeeManager.createEmployee(cmd);
	}

	private CreateEmployeeCommand prepareCreateEmployeeCommand() {
		CreateEmployeeCommand cmd = new CreateEmployeeCommand();
		cmd.setEmployeeId(new EmployeeId(1L));
		cmd.setPosition(new Position("Position"));
		cmd.setFirstName("John");
		cmd.setLastName("Doe");
		return cmd;
	}

}
