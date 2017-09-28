package pl.przygudzki.hourly.shift;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.przygudzki.hourly.commons.commands.InvalidCommandException;
import pl.przygudzki.hourly.commons.commands.Validatable;
import pl.przygudzki.hourly.shift.dto.AddShiftCommand;
import pl.przygudzki.hourly.shift.dto.EditShiftCommand;
import pl.przygudzki.hourly.shift.dto.ShiftNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StandardShiftManagerTest {

	@Mock
	private ShiftRepository shiftRepository;

	private ShiftManager shiftManager;

	@Mock
	private Validatable.ValidationErrors errors;

	private CommandPreparer given = new CommandPreparer();

	@Mock
	private AddShiftCommand addCommand = given.validAddShiftCommand();

	private AddShiftCommand validAddCommand = given.validAddShiftCommand();

	@Mock
	private EditShiftCommand editCommand;

	@Mock
	private Shift shift;

	@Mock
	private ShiftId shiftId;

	@Before
	public void setUp() throws Exception {
		shiftManager = new StandardShiftManager(shiftRepository);

		when(addCommand.getStartDate()).thenReturn(validAddCommand.getStartDate());
		when(addCommand.getEndDate()).thenReturn(validAddCommand.getEndDate());
		when(shiftRepository.get(shiftId)).thenReturn(Optional.of(shift));
	}

	@Test
	public void shouldValidateCreateCommand() {
		shiftManager.addShift(addCommand);

		verify(addCommand).validate(any());
	}

	@Test
	public void shouldCreateAndPersistShift() {
		shiftManager.addShift(addCommand);

		verify(shiftRepository).put(any(Shift.class));
	}

	@Test
	public void shouldFetchShiftsFromRepositoryOnList() {
		shiftManager.listShifts();

		verify(shiftRepository).getAll();
	}

	@Test
	public void shouldValidateCommandOnEdit() {
		shiftManager.editShift(shiftId, editCommand);

		verify(editCommand).validate(any());
	}

	@Test
	public void shouldFetchShiftFromRepositoryOnEdit() {
		shiftManager.editShift(shiftId, editCommand);

		verify(shiftRepository).get(shiftId);
	}

	@Test
	public void shouldThrowWhenEditingNonexistentShift() {
		shiftId = ShiftId.generate();

		Throwable thrown = catchThrowable(() -> shiftManager.editShift(shiftId, editCommand));

		assertThat(thrown).isInstanceOf(ShiftNotFoundException.class);
	}

	@Test
	public void shouldEditShiftOnEdit() {
		shiftManager.editShift(shiftId, editCommand);

		verify(shift).edit(editCommand);
	}

	@Test
	public void shouldRemoveShiftOnRemove() {
		shiftManager.remove(shiftId);

		verify(shift).remove();
	}

	@Test
	public void shoulThrowWhenRemovingNonexistentShift() {
		shiftId = ShiftId.generate();

		Throwable thrown = catchThrowable(() -> shiftManager.remove(shiftId));

		assertThat(thrown).isInstanceOf(ShiftNotFoundException.class);
	}

}