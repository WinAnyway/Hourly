package pl.przygudzki.hourly.employee;

import org.junit.Test;
import pl.przygudzki.hourly.commons.commands.InvalidCommandException;
import pl.przygudzki.hourly.employee.dto.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class EmployeeAcceptanceTest {

	private static final long NON_EXISTENT_POSITION_ID = 123L;
	private EmployeePreparer given = new EmployeePreparer();
	private EmployeeManager employeeManager = new EmployeeConfiguration().employeeManager();

	@Test
	public void shouldAddPosition() {
		// given a valid AddPositionCommand
		AddPositionCommand command = given.validAddPositionCommand();

		// when we add a positionTitle
		employeeManager.addPosition(command);

		// then system has a positionTitle
		List<PositionDto> positionDtos = new LinkedList<>(employeeManager.listPositions());
		assertThat(positionDtos.size()).isEqualTo(1);
		assertThat(positionDtos.get(0).getTitle()).isEqualTo(command.getTitle());
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
		// given two Positions added to the system
		AddPositionCommand command1 = given.validAddPositionCommand();
		AddPositionCommand command2 = given.anotherValidAddPositionCommand();
		employeeManager.addPosition(command1);
		employeeManager.addPosition(command2);

		// when we list Positions that exist in the system
		List<PositionDto> positions = new LinkedList<>(employeeManager.listPositions());

		// then system returns a list of two positions
		assertThat(positions.size()).isEqualTo(2);
		assertThat(positions.get(0).getTitle()).isEqualTo(command1.getTitle());
		assertThat(positions.get(1).getTitle()).isEqualTo(command2.getTitle());
	}

	@Test
	public void shouldEditPosition() {
		// given a Position exists within the system
		employeeManager.addPosition(given.validAddPositionCommand());
		PositionDto positionDto = new LinkedList<>(employeeManager.listPositions()).getFirst();
		// and an EditPositionCommand that edits that position
		EditPositionCommand command = given.editPositionCommandFromPositionDto(positionDto);
		String newTitle = "Barista";
		command.setTitle(newTitle);

		// when we edit the Position
		employeeManager.editPosition(command, positionDto.getId());

		// then the system reflects the edits we made
		PositionDto positionDtoAfter = new LinkedList<>(employeeManager.listPositions()).getFirst();
		assertThat(positionDtoAfter.getTitle()).isEqualTo(newTitle);
	}

	@Test
	public void shouldRemovePosition() {
		// given a Position exists within the system
		employeeManager.addPosition(given.validAddPositionCommand());
		PositionDto positionDto = new LinkedList<>(employeeManager.listPositions()).getFirst();

		// when we remove the Position
		employeeManager.removePosition(positionDto.getId());

		// then the Position is no longer available in the system
		assertThat(catchThrowable(() -> employeeManager.getPositionOrThrow(positionDto.getId())))
				.isInstanceOf(PositionNotFoundException.class);
	}

	@Test
	public void shouldNotListRemovedPositions() {
		// given a Position that was removed from the system
		employeeManager.addPosition(given.validAddPositionCommand());
		PositionDto positionDto = new LinkedList<>(employeeManager.listPositions()).getFirst();
		employeeManager.removePosition(positionDto.getId());
		assertThat(catchThrowable(() -> employeeManager.getPositionOrThrow(positionDto.getId())))
				.isInstanceOf(PositionNotFoundException.class);

		// when we list Positions that are available in the system
		Collection<PositionDto> positionDtos = employeeManager.listPositions();

		// the removed position is not shown
		assertThat(positionDtos).isEmpty();
	}

	@Test
	public void shouldAddEmployee() {
		// given a valid AddEmployeeCommand
		AddEmployeeCommand command = given.validAddEmployeeCommand();
		// and that a suitable position exists in the system
		employeeManager.addPosition(given.validAddPositionCommand());
		// we get the id of desired position
		Long positionId = new LinkedList<>(employeeManager.listPositions()).getFirst().getId();
		// and we apply it to the command
		command.setPositionId(positionId);

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
		command.setPositionId(null);

		// when we try to add an employee
		Throwable thrown = catchThrowable(() -> employeeManager.addEmployee(command));

		// then system throws exception
		assertThat(thrown).isInstanceOf(InvalidCommandException.class);
	}

	@Test
	public void shouldNotAddEmployeeWithPositionNotAddedToSystem() {
		// given the position is not in the system
		assertThat(catchThrowable(() -> employeeManager.getPositionOrThrow(NON_EXISTENT_POSITION_ID)))
				.isInstanceOf(PositionNotFoundException.class);
		// and a command that refers to that nonexistent position
		AddEmployeeCommand command = given.validAddEmployeeCommand();
		command.setPositionId(NON_EXISTENT_POSITION_ID);

		// when we try to add an employee
		Throwable thrown = catchThrowable(() -> employeeManager.addEmployee(command));

		// then system throws exception
		assertThat(thrown).isInstanceOf(PositionNotFoundException.class);
	}

}
