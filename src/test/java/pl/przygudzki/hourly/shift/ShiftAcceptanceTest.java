package pl.przygudzki.hourly.shift;

import org.junit.Test;
import pl.przygudzki.hourly.commons.commands.InvalidCommandException;
import pl.przygudzki.hourly.shift.dto.AddShiftCommand;
import pl.przygudzki.hourly.shift.dto.EditShiftCommand;
import pl.przygudzki.hourly.shift.dto.ShiftDto;
import pl.przygudzki.hourly.shift.dto.ShiftNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ShiftAcceptanceTest {

	private static final String REMOVED_STATUS = "REMOVED";
	private static final ShiftId NONEXISTENT_SHIFT_ID = ShiftId.generate();

	private ShiftManager shiftManager = new ShiftConfiguration().shiftManager();

	private CommandPreparer givenCommand = new CommandPreparer();
	private ShiftPreparer given = new ShiftPreparer(shiftManager);

	@Test
	public void shouldAddShift() {
		// given a valid AddShiftCommand
		AddShiftCommand addShiftCommand = givenCommand.validAddShiftCommand();

		// when we create a shift
		shiftManager.addShift(addShiftCommand);

		// the shift is available in the system
		Collection<ShiftDto> shiftDtos = shiftManager.listShifts();
		assertThat(shiftDtos.size()).isEqualTo(1);
	}

	@Test
	public void shouldNotAddShiftWithInvalidData() {
		// given an invalid AddShiftCommand
		AddShiftCommand command = givenCommand.invalidAddShiftCommand();

		// when we try to create a shift
		Throwable thrown = catchThrowable(() -> shiftManager.addShift(command));

		// then an exception is thrown
		assertThat(thrown).isInstanceOf(InvalidCommandException.class);
		assertThat(((InvalidCommandException) thrown).getErrors().getErrors())
				.hasSize(2)
				.containsEntry("startDate", Collections.singleton("must occur before endDate"))
				.containsEntry("endDate", Collections.singleton("must occur after startDate"));
	}

	@Test
	public void shouldNotAddShiftWithoutData() {
		// given an empty AddShiftCommand
		AddShiftCommand command = new AddShiftCommand();

		// when we try to create a shift
		Throwable thrown = catchThrowable(() -> shiftManager.addShift(command));

		// then an exception is thrown
		assertThat(thrown).isInstanceOf(InvalidCommandException.class);
	}

	@Test
	public void shouldGetShift() {
		// given there is a shift available in the system
		AddShiftCommand command = givenCommand.validAddShiftCommand();
		ShiftId shiftId = given.newShiftIsAdded(command).getId();

		// when we try to query it
		ShiftDto shiftDto = shiftManager.getShift(shiftId);

		// then we get it
		assertThatShiftDtosReflectCreateShiftCommands(Collections.singletonList(shiftDto), command);
	}

	@Test
	public void shouldThrowWhenGettingNonexistentShift() {
		// given the shift does not exist
		assertThat(shiftManager.listShifts()).isEmpty();

		// when we try to query a shift
		Throwable thrown = catchThrowable(() -> shiftManager.getShift(givenCommand.invalidShiftId()));

		// then an exception is thrown
		assertThat(thrown).isInstanceOf(ShiftNotFoundException.class);
	}

	@Test
	public void shouldListShifts() {
		// given there are multiple shifts available in the system
		AddShiftCommand command1 = givenCommand.validAddShiftCommand();
		AddShiftCommand command2 = givenCommand.anotherValidAddShiftCommand();
		shiftManager.addShift(command1);
		shiftManager.addShift(command2);

		// when we query a list of all shifts
		Collection<ShiftDto> shiftDtos = shiftManager.listShifts();

		// then all the shifts are listed
		assertThatShiftDtosReflectCreateShiftCommands(shiftDtos, command1, command2);
	}

	@Test
	public void shouldEditShift() {
		// given a shift is available in the system
		ShiftDto beforeShiftDto = given.newShiftIsAdded();
		EditShiftCommand command = givenCommand.editShiftCommandFromDto(beforeShiftDto);

		// when we try to edit the shift
		shiftManager.editShift(beforeShiftDto.getId(), command);

		// then the shift reflects our changes
		ShiftDto shiftDto = shiftManager.getShift(beforeShiftDto.getId());
		assertThatShiftDtosReflectEditShiftCommands(Collections.singletonList(shiftDto), command);
	}

	@Test
	public void shouldNotEditShiftWithInvalidData() {
		// given a shift exists in the system
		ShiftDto shiftDto = given.newShiftIsAdded();
		EditShiftCommand command = givenCommand.editCommandWithDatesOutOfOrder();

		// when we try to edit the shift
		Throwable thrown = catchThrowable(() -> shiftManager.editShift(shiftDto.getId(), command));

		// then an exception is thrown
		assertThat(thrown).isInstanceOf(InvalidCommandException.class);
		assertThat(((InvalidCommandException) thrown).getErrors().getErrors())
				.hasSize(2)
				.containsEntry("startDate", Collections.singleton("must occur before endDate"))
				.containsEntry("endDate", Collections.singleton("must occur after startDate"));
	}

	@Test
	public void shouldThrowWhenEditingNonexistentShift() {
		// given a shift does not exist in the system
		assertThat(catchThrowable(() -> shiftManager.getShift(NONEXISTENT_SHIFT_ID))).isInstanceOf(ShiftNotFoundException.class);

		// when we try to edit a nonexistent shift
		Throwable thrown = catchThrowable(() -> shiftManager.editShift(NONEXISTENT_SHIFT_ID, givenCommand.validEditShiftCommand()));

		// then an exception is thrown
		assertThat(thrown).isInstanceOf(ShiftNotFoundException.class);
	}

	@Test
	public void shouldRemoveShift() {
		// given there is a shift in the system
		ShiftId shiftId = given.newShiftIsAdded().getId();

		// when we remove the shift
		shiftManager.remove(shiftId);

		// then the shift has status removed
		ShiftDto shiftDto = shiftManager.getShift(shiftId);
		assertThat(shiftDto.getStatus()).isEqualTo(REMOVED_STATUS);
	}

	@Test
	public void shouldThrowWhenRemovingNonexistentShift() {
		// given a shift does not exist in the system
		assertThat(catchThrowable(() -> shiftManager.getShift(NONEXISTENT_SHIFT_ID))).isInstanceOf(ShiftNotFoundException.class);

		// when we try to edit a nonexistent shift
		Throwable thrown = catchThrowable(() -> shiftManager.remove(NONEXISTENT_SHIFT_ID));

		// then an exception is thrown
		assertThat(thrown).isInstanceOf(ShiftNotFoundException.class);
	}

	@Test
	public void shouldNotListRemovedShifts() {
		// given a shift is removed from the system
		ShiftId shiftId = given.newRemovedShift();

		// when we list all shifts
		Collection<ShiftDto> shiftDtos = shiftManager.listShifts();

		// then the shift is no on the list
		assertThat(
				shiftDtos.stream()
						.filter(shiftDto -> shiftDto.getId().equals(shiftId))
		).isEmpty();
	}

	@Test
	public void shouldGetRemovedShift() {
		// given a shift is removed from the system
		ShiftId shiftId = given.newRemovedShift();

		// when we get the shift
		ShiftDto shiftDto = shiftManager.getShift(shiftId);

		// the shift is returned
		assertThat(shiftDto.getStatus()).isEqualTo(REMOVED_STATUS);
	}

	private void assertThatShiftDtosReflectEditShiftCommands(Collection<ShiftDto> dtos, EditShiftCommand... commands) {
		assertThat(dtos.size()).isEqualTo(commands.length);
		assertThat(Arrays.stream(commands))
				.allMatch(command -> dtos.stream()
						.anyMatch(shiftDto -> shiftDtoReflectsEditShiftCommand(shiftDto, command)));
	}

	private boolean shiftDtoReflectsEditShiftCommand(ShiftDto shiftDto, EditShiftCommand command) {
		return shiftDto.getStartDate().equals(command.getStartDate())
				&& shiftDto.getEndDate().equals(command.getEndDate());
	}

	private void assertThatShiftDtosReflectCreateShiftCommands(Collection<ShiftDto> dtos, AddShiftCommand... commands) {
		assertThat(dtos.size()).isEqualTo(commands.length);
		assertThat(Arrays.stream(commands))
				.allMatch(command -> dtos.stream()
						.anyMatch(shiftDto -> shiftDtoReflectsAddShiftCommand(shiftDto, command)));
	}

	private boolean shiftDtoReflectsAddShiftCommand(ShiftDto shiftDto, AddShiftCommand command) {
		return shiftDto.getStartDate().equals(command.getStartDate())
				&& shiftDto.getEndDate().equals(command.getEndDate());
	}

}
