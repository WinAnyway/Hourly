package pl.przygudzki.hourly.api;

import org.junit.Before;
import org.junit.Test;
import pl.przygudzki.hourly.api.implementation.StandardShiftManager;
import pl.przygudzki.hourly.domain.commands.CreateShiftCommand;
import pl.przygudzki.hourly.domain.shift.ShiftRepository;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ShiftManagerTest {

	private ShiftManager shiftManager;
	private ShiftRepository shiftRepository;

	@Before
	public void setUp() {
		shiftRepository = mock(ShiftRepository.class);
		shiftManager = new StandardShiftManager(shiftRepository);
	}

	@Test
	public void shouldAddShift() {
		CreateShiftCommand command = prepareCreateShiftCommand();
		shiftManager.createShift(command);
		verify(shiftRepository).put(any());
	}

	private CreateShiftCommand prepareCreateShiftCommand() {
		CreateShiftCommand command = new CreateShiftCommand();
		command.setStartDate(LocalDateTime.of(2017, 7, 1, 12, 0));
		command.setEndDate(LocalDateTime.of(2017, 7, 1, 16, 0));
		return command;
	}

}
