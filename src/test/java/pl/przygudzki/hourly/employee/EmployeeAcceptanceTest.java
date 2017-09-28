package pl.przygudzki.hourly.employee;

import org.junit.Before;
import org.junit.Test;
import pl.przygudzki.hourly.commons.commands.InvalidCommandException;
import pl.przygudzki.hourly.employee.dto.*;
import pl.przygudzki.hourly.position.PositionConfiguration;
import pl.przygudzki.hourly.position.PositionId;
import pl.przygudzki.hourly.position.PositionManager;
import pl.przygudzki.hourly.position.PositionPreparer;
import pl.przygudzki.hourly.position.dto.PositionDto;
import pl.przygudzki.hourly.position.dto.PositionNotAvailableException;
import pl.przygudzki.hourly.position.dto.PositionNotFoundException;

import java.util.*;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class EmployeeAcceptanceTest {

	private static final String REMOVED_EMPLOYEE_STATUS = "REMOVED";

	private PositionManager positionManager = new PositionConfiguration().positionManager();
	private EmployeeManager employeeManager = new EmployeeConfiguration().employeeManager(positionManager);

	private EmployeeCommandPreparer givenCommand = new EmployeeCommandPreparer();
	private PositionPreparer givenPosition = new PositionPreparer(positionManager);
	private EmployeePreparer given = new EmployeePreparer(employeeManager, givenPosition);

	private PositionId positionId;

	@Before
	public void setUp() throws Exception {
		positionId = givenPosition.newPositionIsAdded().getId();
	}

	@Test
	public void shouldAddEmployee() {
		// given a position exists in the system
		PositionDto positionDto = givenPosition.newPositionIsAdded();
		// and given a valid AddEmployeeCommand referencing the position
		AddEmployeeCommand command = givenCommand.validAddEmployeeCommand(positionDto.getId());

		// when we add an employee
		employeeManager.addEmployee(command);

		// then the employee exists in the system
		Collection<EmployeeDto> employeeDtos = employeeManager.listEmployees();
		assertThatEmployeeDtosReflectAddCommands(employeeDtos, command);
	}

	@Test
	public void shouldNotAddEmployeeWithoutData() {
		// given an empty command
		AddEmployeeCommand command = new AddEmployeeCommand();

		// when we try to add an employee
		Throwable thrown = catchThrowable(() -> employeeManager.addEmployee(command));

		// then the system throws exception
		assertThat(thrown).isInstanceOf(InvalidCommandException.class);
	}

	@Test
	public void shouldNotAddEmployeeWithInvalidData() {
		// given a command with invalid data
		AddEmployeeCommand command = givenCommand.invalidAddEmployeeCommand();

		// when we try to add an employee
		Throwable thrown = catchThrowable(() -> employeeManager.addEmployee(command));

		// then the system throws exception
		assertThat(thrown).isInstanceOf(InvalidCommandException.class);
	}

	@Test
	public void shouldNotAddEmployeeWithNonexistentPosition() {
		// given the position is not in the system
		AddEmployeeCommand command = givenCommand.validAddEmployeeCommand(givenCommand.nonExistentPositionId());

		// when we try to add an employee
		Throwable thrown = catchThrowable(() -> employeeManager.addEmployee(command));

		// then the system throws exception
		assertThat(thrown).isInstanceOf(PositionNotFoundException.class);
	}

	@Test
	public void shouldGetEmployee() {
		// given an employee is available in the system
		PositionId positionId = givenPosition.newPositionIsAdded().getId();
		AddEmployeeCommand command = givenCommand.validAddEmployeeCommand(positionId);
		EmployeeId employeeId = given.newEmployeeIsAdded(command).getId();

		// when we try to query the employee
		EmployeeDto employeeDto = employeeManager.getEmployee(employeeId);

		// then the employee is fetched
		assertThatEmployeeDtosReflectAddCommands(Collections.singletonList(employeeDto), command);
	}

	@Test
	public void shouldThrowWhenGettingNonexistentEmployee() {
		// given the employee does not exist in the system
		assertThat(employeeManager.listEmployees()).isEmpty();

		// when we try to query the employee
		Throwable thrown = catchThrowable(() -> employeeManager.getEmployee(givenCommand.nonExistentEmployeeId()));

		// then an exception is thrown
		assertThat(thrown).isInstanceOf(EmployeeNotFoundException.class);
	}

	@Test
	public void shouldListEmployees() {
		// given the employees exist in the system
		AddEmployeeCommand command1 = givenCommand.validAddEmployeeCommand(positionId);
		AddEmployeeCommand command2 = givenCommand.anotherValidAddEmployeeCommand(positionId);
		employeeManager.addEmployee(command1);
		employeeManager.addEmployee(command2);

		// when we list all employees
		List<EmployeeDto> employeeDtos = new LinkedList<>(employeeManager.listEmployees());

		// than the system returns a list of two employees
		assertThatEmployeeDtosReflectAddCommands(employeeDtos, command1, command2);
	}

	@Test
	public void shouldEditEmployee() {
		// given the employee exists in the system
		EmployeeDto employeeDtoBefore = given.newEmployeeIsAdded();
		EditEmployeeCommand command = givenCommand.editEmployeeCommandFrom(employeeDtoBefore);

		// when we try to edit the employee
		employeeManager.editEmployee(employeeDtoBefore.getId(), command);

		// then the system reflects the changes
		Collection<EmployeeDto> employeeDtos = employeeManager.listEmployees();
		assertThatEmployeeDtosReflectEditCommands(employeeDtos, command);
	}

	@Test
	public void shouldThrowWhenEditingEmployeeWithInvalidCommand() {
		// given the employee exists in the system
		EmployeeDto employeeDtoBefore = given.newEmployeeIsAdded();
		// and given an invalid command
		EditEmployeeCommand command = givenCommand.invalidEditEmployeeCommand();

		// when we try to edit the employee
		Throwable thrown = catchThrowable(() -> employeeManager.editEmployee(employeeDtoBefore.getId(), command));

		// then an exception is thrown
		assertThat(thrown).isInstanceOf(InvalidCommandException.class);
	}

	@Test
	public void shouldThrowWhenEditingEmployeeWithNonexistentPosition() {
		// given the employee exists in the system
		EmployeeDto employeeDto = given.newEmployeeIsAdded();
		// and given a command referencing a nonexistent position
		EditEmployeeCommand command = givenCommand.editEmployeeCommandFrom(employeeDto);
		command.setPositionId(givenCommand.nonExistentPositionId());

		// when we try to edit the employee
		Throwable thrown = catchThrowable(() -> employeeManager.editEmployee(employeeDto.getId(), command));

		// then an exception is thrown
		assertThat(thrown).isInstanceOf(PositionNotFoundException.class);
	}

	@Test
	public void shouldThrowWhenEditingEmployeeWithRemovedPosition() {
		// given the employee exists in the system
		PositionId positionId = givenPosition.newPositionIsAdded().getId();
		EmployeeDto employeeDto = given.newEmployeeIsAdded(positionId);
		// and a command referencing a removed position
		positionManager.removePosition(positionId);
		EditEmployeeCommand command = givenCommand.editEmployeeCommandFrom(employeeDto);

		// when we try to assign the removed position by editing the employee
		Throwable thrown = catchThrowable(() -> employeeManager.editEmployee(employeeDto.getId(), command));

		// then an exception is thrown
		assertThat(thrown).isInstanceOf(PositionNotAvailableException.class);
	}

	@Test
	public void shouldthrowWhenEditingNonexistentEmployee() {
		// given the employee does not exist in the system
		PositionId positionId = givenPosition.newPositionIsAdded().getId();
		assertThat(catchThrowable(() -> employeeManager.getEmployee(givenCommand.nonExistentEmployeeId()))).isInstanceOf(EmployeeNotFoundException.class);

		// when we try to edit the employee
		Throwable thrown = catchThrowable(() -> employeeManager.editEmployee(givenCommand.nonExistentEmployeeId(), givenCommand.validEditEmployeeCommand(positionId)));

		// then an exception is thrown
		assertThat(thrown).isInstanceOf(EmployeeNotFoundException.class);
	}

	@Test
	public void shouldRemoveEmployee() {
		// given the employee exists in the system
		EmployeeId employeeId = given.newEmployeeIsAdded().getId();

		// when we try to remove the employee
		employeeManager.removeEmployee(employeeId);

		// then the employee has status REMOVED
		EmployeeDto employeeDto = employeeManager.getEmployee(employeeId);
		assertThat(employeeDto.getStatus()).isEqualTo(REMOVED_EMPLOYEE_STATUS);
	}

	@Test
	public void shouldThrowWhenRemovingNonexistentEmployee() {
		// given the employee does not exist in the system
		assertThat(catchThrowable(() -> employeeManager.getEmployee(givenCommand.nonExistentEmployeeId()))).isInstanceOf(EmployeeNotFoundException.class);

		// when we try to remove the employee
		Throwable thrown = catchThrowable(() -> employeeManager.removeEmployee(givenCommand.nonExistentEmployeeId()));

		// then an exception is thrown
		assertThat(thrown).isInstanceOf(EmployeeNotFoundException.class);
	}

	@Test
	public void shouldNotListRemovedEmployees() {
		// given the employee was removed from the system
		EmployeeId employeeId = given.newEmployeeIsAdded().getId();
		employeeManager.removeEmployee(employeeId);

		// when we list employees
		Collection<EmployeeDto> employeeDtos = employeeManager.listEmployees();

		// then the employee does not show up on the list
		assertThat(
				employeeDtos.stream()
						.filter(employeeDto -> employeeDto.getId().equals(employeeId))
		).isEmpty();
	}

	private void assertThatEmployeeDtosReflectAddCommands(Collection<EmployeeDto> dtos, AddEmployeeCommand... commands) {
		assertThat(dtos.size()).isEqualTo(commands.length);
		assertThat(Arrays.stream(commands))
				.allMatch(command -> dtos.stream()
						.anyMatch(employeeDtoReflectsAddEmployeeCommand(command)));
	}

	private Predicate<EmployeeDto> employeeDtoReflectsAddEmployeeCommand(AddEmployeeCommand command) {
		return employeeDto -> employeeDto.getFirstName().equals(command.getFirstName())
				&& employeeDto.getLastName().equals(command.getLastName())
				&& employeeDto.getPosition().getId().equals(command.getPositionId());
	}

	private void assertThatEmployeeDtosReflectEditCommands(Collection<EmployeeDto> dtos, EditEmployeeCommand... commands) {
		assertThat(dtos.size()).isEqualTo(commands.length);
		assertThat(Arrays.stream(commands))
				.allMatch(command -> dtos.stream()
						.anyMatch(employeeDtoReflectsEditEmployeeCommand(command)));
	}

	private Predicate<EmployeeDto> employeeDtoReflectsEditEmployeeCommand(EditEmployeeCommand command) {
		return employeeDto -> employeeDto.getFirstName().equals(command.getFirstName())
				&& employeeDto.getLastName().equals(command.getLastName())
				&& employeeDto.getPosition().getId().equals(command.getPositionId());
	}

}
