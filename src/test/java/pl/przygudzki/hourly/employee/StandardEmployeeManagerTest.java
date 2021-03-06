package pl.przygudzki.hourly.employee;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StandardEmployeeManagerTest {

	@Mock
	private EmployeeRepository employeeRepository;

	private EmployeeManager employeeManager;

	private EmployeePreparer given = new EmployeePreparer();

	private AddEmployeeCommand command;

	@Before
	public void setUp() {
		employeeManager = new StandardEmployeeManager(employeeRepository);
		command = given.validCreateEmployeeCommand();
	}

	@Test
	public void shouldCreateAndPersistEmployeeOnAdd() {
		employeeManager.addEmployee(command);

		verify(employeeRepository, times(1)).save(any());
	}

	@Test
	public void shouldQueryEmployeesOnShow() {
		employeeManager.show();

		verify(employeeRepository, times(1)).findAll();
	}

}
