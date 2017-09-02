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

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private PositionRepository positionRepository;

	private EmployeeManager employeeManager;
	private EmployeePreparer given = new EmployeePreparer();
	private AddEmployeeCommand command;

	@Before
	public void setUp() {
		employeeManager = new StandardEmployeeManager(employeeRepository, positionRepository);
		command = given.validAddEmployeeCommand();
	}

	@Test
	public void shouldCreateAndPersistEmployeeOnAdd() {
		String title = "Manager";
		command.setPositionTitle(title);
		when(positionRepository.get(title)).thenReturn(Optional.of(Position.of(title)));

		employeeManager.addEmployee(command);

		verify(employeeRepository, times(1)).put(any());
	}

	@Test
	public void shouldQueryEmployeesOnShow() {
		employeeManager.showEmployees();

		verify(employeeRepository, times(1)).getAll();
	}

}
