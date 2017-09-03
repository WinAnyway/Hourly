package pl.przygudzki.hourly.employee;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.przygudzki.hourly.employee.dto.AddEmployeeCommand;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StandardEmployeeManagerTest {

	private static final long POSITION_ID = 123L;

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private PositionRepository positionRepository;

	@Mock
	private Position position;

	private EmployeeManager employeeManager;
	private EmployeePreparer given = EmployeePreparer.withInternalEmployeeManager();
	private AddEmployeeCommand command;

	@Before
	public void setUp() {
		employeeManager = new StandardEmployeeManager(employeeRepository, positionRepository);
		command = given.validAddEmployeeCommand();
	}

	@Test
	public void shouldCreateAndPersistEmployeeOnAdd() {
		command.setPositionId(POSITION_ID);
		when(positionRepository.get(PositionId.of(POSITION_ID))).thenReturn(Optional.of(position));
		when(position.isAvailable()).thenReturn(true);

		employeeManager.addEmployee(command);

		verify(employeeRepository, times(1)).put(any());
	}

	@Test
	public void shouldQueryEmployeesOnShow() {
		employeeManager.listEmployees();

		verify(employeeRepository, times(1)).getAll();
	}

}
