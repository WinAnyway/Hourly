package pl.przygudzki.hourly.shift;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ShiftTest {


	private CreateShiftCommand command;

	@Before
	public void setUp() throws Exception {
		command = prepareCreateShiftCommand();
	}

	@Test
	public void shouldCreateShift() {
		Shift shift = Shift.create(command);
		assertThat(shift).isNotNull();
	}

	@Test
	public void shouldGenerateUniqueIds() {
		Shift shift = Shift.create(command);
		Shift otherShift = Shift.create(command);
		assertThat(shift.getId().equals(otherShift.getId())).isFalse();
	}

	@Test
	public void shouldThrowExceptionWhenCreatingShiftWithStartDateAfterEndDate() {
		command.setStartDate(LocalDateTime.of(2017, 7, 1, 16, 0));
		command.setEndDate(LocalDateTime.of(2017, 7, 1, 12, 0));
		Throwable thrown = catchThrowable(() -> Shift.create(command));
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
		assertThat(thrown).hasMessage("startDate must be earlier than endDate");
	}

	private CreateShiftCommand prepareCreateShiftCommand() {
		CreateShiftCommand command = new CreateShiftCommand();
		command.setStartDate(LocalDateTime.of(2017, 7, 1, 12, 0));
		command.setEndDate(LocalDateTime.of(2017, 7, 1, 16, 0));
		return command;
	}

}