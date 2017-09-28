package pl.przygudzki.hourly.position;

import org.junit.Test;
import pl.przygudzki.hourly.commons.commands.InvalidCommandException;
import pl.przygudzki.hourly.position.dto.AddPositionCommand;
import pl.przygudzki.hourly.position.dto.EditPositionCommand;
import pl.przygudzki.hourly.position.dto.PositionDto;
import pl.przygudzki.hourly.position.dto.PositionNotFoundException;

import java.util.*;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class PositionAcceptanceTest {

	private PositionManager positionManager = new PositionConfiguration().positionManager();

	private PositionCommandPreparer givenCommand = new PositionCommandPreparer();
	private PositionPreparer given = new PositionPreparer(positionManager);

	@Test
	public void shouldAddPosition() {
		// given a valid AddPositionCommand
		AddPositionCommand command = givenCommand.validAddPositionCommand();

		// when we add a position to the system
		positionManager.addPosition(command);

		// then the position exists in the system
		List<PositionDto> positionDtos = new LinkedList<>(positionManager.listPositions());
		assertThat(positionDtos.size()).isEqualTo(1);
		assertThat(positionDtos.get(0).getTitle()).isEqualTo(command.getTitle());
	}

	@Test
	public void shouldNotAddPositionWithoutTitle() {
		// given an empty command
		AddPositionCommand command = new AddPositionCommand();

		// when we try to add an employee
		Throwable thrown = catchThrowable(() -> positionManager.addPosition(command));

		// then system throws exception
		assertThat(thrown).isInstanceOf(InvalidCommandException.class);
	}

	@Test
	public void shouldNotAddPositionWithInvalidData() {
		// given a command with invalid data
		AddPositionCommand command = new AddPositionCommand();
		command.setTitle("");

		Throwable thrown = catchThrowable(() -> positionManager.addPosition(command));

		// then system throws exception
		assertThat(thrown).isInstanceOf(InvalidCommandException.class);
	}

	@Test
	public void shouldGetPosition() {
		// given a position is available in the system
		AddPositionCommand command = givenCommand.validAddPositionCommand();
		PositionId positionId = given.newPositionIsAdded(command).getId();

		// when we query a position
		PositionDto positionDto = positionManager.getPosition(positionId);

		// then the position is fetched
		assertThatPositionDtosReflectAddCommands(Collections.singleton(positionDto), command);
	}

	@Test
	public void shouldThrowWhenGettingNonexistentPosition() {
		// given a position is not available in the system
		assertThat(positionManager.listPositions()).isEmpty();

		// when we query a position
		Throwable thrown = catchThrowable(() -> positionManager.getPosition(givenCommand.nonExistentPositionId()));

		// then an exception is thrown
		assertThat(thrown).isInstanceOf(PositionNotFoundException.class);
	}

	@Test
	public void shouldListPositions() {
		// given two Positions added to the system
		AddPositionCommand command1 = givenCommand.validAddPositionCommand();
		positionManager.addPosition(command1);
		AddPositionCommand command2 = givenCommand.anotherValidAddPositionCommand();
		positionManager.addPosition(command2);

		// when we list Positions that exist in the system
		List<PositionDto> positionDtos = new LinkedList<>(positionManager.listPositions());

		// then system returns a list of two positions
		assertThatPositionDtosReflectAddCommands(positionDtos, command1, command2);
	}

	@Test
	public void shouldEditPosition() {
		// given a Position that exists within the system
		PositionDto positionDto = given.newPositionIsAdded();
		EditPositionCommand command = givenCommand.editPositionCommandFrom(positionDto);
		String newTitle = "Barista";
		command.setTitle(newTitle);

		// when we edit the Position
		positionManager.editPosition(positionDto.getId(), command);

		// then the system reflects the edits we made
		Collection<PositionDto> positionDtos = positionManager.listPositions();
		assertThatPositionDtosReflectEditCommands(positionDtos, command);
	}

	@Test
	public void shouldNotEditPositionWithInvalidCommand() {
		// given the position exists in the system and invalid command
		PositionDto positionDto = given.newPositionIsAdded();
		EditPositionCommand command = givenCommand.invalidEditPositionCommand();

		// when we try to edit the employee
		Throwable thrown = catchThrowable(() -> positionManager.editPosition(positionDto.getId(), command));

		// then an exception is thrown
		assertThat(thrown).isInstanceOf(InvalidCommandException.class);
	}

	@Test
	public void shouldThrowWhenEditingNonexistentPosition() {
		// given position does not exist in the system
		assertThat(catchThrowable(() -> positionManager.getPosition(givenCommand.nonExistentPositionId()))).isInstanceOf(PositionNotFoundException.class);

		// when we try to edit the nonexistent position
		Throwable thrown = catchThrowable(() -> positionManager.editPosition(givenCommand.nonExistentPositionId(), givenCommand.validEditPositionCommand()));

		// then an exception is thrown
		assertThat(thrown).isInstanceOf(PositionNotFoundException.class);
	}

	@Test
	public void shouldRemovePosition() {
		// given a Position exists within the system
		PositionId positionId = given.newPositionIsAdded().getId();

		// when we remove the Position
		positionManager.removePosition(positionId);

		// then the Position is no longer available in the system
		assertThat(positionManager.getPosition(positionId).getStatus()).isEqualTo("REMOVED");
	}

	@Test
	public void shouldThrowWhenRemovingNonexistentPosition() {
		// given position does not exist in the system
		assertThat(catchThrowable(() -> positionManager.getPosition(givenCommand.nonExistentPositionId()))).isInstanceOf(PositionNotFoundException.class);

		// when we try to edit the nonexistent position
		Throwable thrown = catchThrowable(() -> positionManager.removePosition(givenCommand.nonExistentPositionId()));

		// then an exception is thrown
		assertThat(thrown).isInstanceOf(PositionNotFoundException.class);
	}

	@Test
	public void shouldNotListRemovedPositions() {
		// given a Position that was removed from the system
		PositionId positionId = given.newRemovedPosition();

		// when we list Positions that are available in the system
		Collection<PositionDto> positionDtos = positionManager.listPositions();

		// the removed position is not shown
		assertThat(
				positionDtos.stream()
						.filter(positionDto -> positionDto.getId().equals(positionId))
		).isEmpty();
	}

	private void assertThatPositionDtosReflectAddCommands(Collection<PositionDto> dtos, AddPositionCommand... commands) {
		assertThat(dtos.size()).isEqualTo(commands.length);
		assertThat(Arrays.stream(commands))
				.allMatch(command -> dtos.stream()
						.anyMatch(positionDtoReflectsAddPositionCommand(command)));
	}

	private Predicate<PositionDto> positionDtoReflectsAddPositionCommand(AddPositionCommand command) {
		return positionDto -> positionDto.getTitle().equals(command.getTitle());
	}

	private void assertThatPositionDtosReflectEditCommands(Collection<PositionDto> dtos, EditPositionCommand... commands) {
		assertThat(dtos.size()).isEqualTo(commands.length);
		assertThat(Arrays.stream(commands))
				.allMatch(command -> dtos.stream()
						.anyMatch(positionDtoReflectsEditPositionCommand(command)));
	}

	private Predicate<PositionDto> positionDtoReflectsEditPositionCommand(EditPositionCommand command) {
		return positionDto -> positionDto.getTitle().equals(command.getTitle());
	}

}
