package pl.przygudzki.hourly.employee;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.przygudzki.hourly.employee.dto.AddEmployeeCommand;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StandardEmployeeManagerTest {

	private static final long POSITION_ID = 123L;

	private CommandPreparer given = new CommandPreparer();

	@Mock
	private PositionManager positionManager;

	@Mock
	private EmployeeRepository employeeRepository;

	private EmployeeManager employeeManager;

	private Position position;
	private AddEmployeeCommand command;

	@Before
	public void setUp() {
		employeeManager = new StandardEmployeeManager(positionManager, employeeRepository);
		command = given.validAddEmployeeCommand();
	}

	@Test
	public void shouldCreateAndPersistEmployeeOnAdd() {
		command.setPositionId(POSITION_ID);
		when(positionManager.getPositionOrThrow(POSITION_ID)).thenReturn(position);

		employeeManager.addEmployee(command);

		verify(employeeRepository, times(1)).put(any());
	}

	@Test
	public void shouldQueryEmployeesOnShow() {
		employeeManager.listEmployees();

		verify(employeeRepository, times(1)).getAll();
	}

}
