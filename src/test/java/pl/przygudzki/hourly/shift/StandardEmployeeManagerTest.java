package pl.przygudzki.hourly.shift;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.przygudzki.hourly.employee.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StandardEmployeeManagerTest {

	@Mock
	private EmployeeRepository employeeRepository;

	private EmployeeManager employeeManager;

	private EmployeePreparer given = new EmployeePreparer();

	private CreateEmployeeCommand command;

	@Before
	public void setUp() {
		employeeManager = new StandardEmployeeManager(employeeRepository);
		command = given.validCreateEmployeeCommand();
	}

	@Test
	public void shouldCreateEmployee() {
		employeeManager.createEmployee(command);

		verify(employeeRepository, times(1)).put(any());
	}

}
