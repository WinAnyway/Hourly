package pl.przygudzki.hourly.shift;

import org.junit.Before;
import org.junit.Test;
import pl.przygudzki.hourly.shift.dto.AddShiftCommand;
import pl.przygudzki.hourly.shift.dto.EditShiftCommand;
import pl.przygudzki.hourly.shift.dto.ShiftDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ShiftTest {

	private static final String AVAILABLE_STATUS = "AVAILABLE";
	private static final String REMOVED_STATUS = "REMOVED";

	private Shift shift;

	private CommandPreparer given = new CommandPreparer();

	private ShiftDtoBuilder dtoBuilder = new ShiftDtoBuilder();

	private AddShiftCommand addCommand;
	private EditShiftCommand editCommand;

	@Before
	public void setUp() throws Exception {
		addCommand = given.validAddShiftCommand();
		editCommand = given.validEditShiftCommand();
		shift = Shift.create(addCommand);
	}

	@Test
	public void shouldCreateShift() {
		shift = Shift.create(addCommand);

		assertThat(shift).hasNoNullFieldsOrProperties();
	}

	@Test
	public void shouldStoreDataOnCreate() {
		shift = Shift.create(addCommand);

		ShiftDto shiftDto = buildShiftDto(shift);

		assertThat(shiftDto.getStartDate()).isEqualTo(addCommand.getStartDate());
		assertThat(shiftDto.getEndDate()).isEqualTo(addCommand.getEndDate());
	}

	@Test
	public void shouldAssignAvailableStatusOnCreate() {
		shift = Shift.create(addCommand);

		ShiftDto shiftDto = buildShiftDto(shift);

		assertThat(shiftDto.getStatus()).isEqualTo(AVAILABLE_STATUS);
	}

	@Test
	public void shouldGenerateUniqueIds() {
		Shift shift = Shift.create(addCommand);
		Shift otherShift = Shift.create(addCommand);

		assertThat(shift.getId().equals(otherShift.getId())).isFalse();
	}

	@Test
	public void shouldThrowExceptionOnCreateWhenDatesAreOutOfOrder() {
		addCommand = given.addCommandWithDatesOutOfOrder();

		Throwable thrown = catchThrowable(() -> Shift.create(addCommand));

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
		assertThat(thrown).hasMessage("startDate must be earlier than endDate");
	}

	@Test
	public void shouldStoreNewDataOnEdit() {
		shift.edit(editCommand);

		ShiftDto shiftDto = buildShiftDto(shift);

		assertThat(shiftDto.getStartDate()).isEqualTo(editCommand.getStartDate());
		assertThat(shiftDto.getEndDate()).isEqualTo(editCommand.getEndDate());
	}

	@Test
	public void shouldThrowExceptionOnEditWhenDatesAreOutOfOrder() {
		editCommand = given.editCommandWithDatesOutOfOrder();

		Throwable thrown = catchThrowable(() -> shift.edit(editCommand));

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
		assertThat(thrown).hasMessage("startDate must be earlier than endDate");
	}

	@Test
	public void shouldAssignRemovedStatusOnRemove() {
		shift.remove();

		ShiftDto shiftDto = buildShiftDto(shift);
		assertThat(shiftDto.getStatus()).isEqualTo(REMOVED_STATUS);
	}

	private ShiftDto buildShiftDto(Shift shift) {
		shift.export(dtoBuilder);
		return dtoBuilder.build();
	}

}
