package pl.przygudzki.hourly.employee;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.przygudzki.hourly.employee.dto.AddEmployeeCommand;
import pl.przygudzki.hourly.employee.dto.EditEmployeeCommand;
import pl.przygudzki.hourly.employee.dto.EmployeeNotFoundException;
import pl.przygudzki.hourly.position.dto.PositionNotAvailableException;
import pl.przygudzki.hourly.position.PositionId;
import pl.przygudzki.hourly.position.PositionManager;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StandardEmployeeManagerTest {

	@Mock
	private PositionManager positionManager;

	@Mock
	private EmployeeRepository employeeRepository;

	private StandardEmployeeManager employeeManager;

	private EmployeeCommandPreparer given = new EmployeeCommandPreparer();

	@Mock
	private Employee employee;

	@Mock
	private AddEmployeeCommand addCommand;

	@Mock
	private EditEmployeeCommand editCommand;

	private PositionId positionId = given.validPositionId();
	private EmployeeId employeeId = given.validEmployeeId();
	private EmployeeId nonexistentEmployeeId = given.nonExistentEmployeeId();

	@Before
	public void setUp() {
		employeeManager = new StandardEmployeeManager(positionManager, employeeRepository);

		when(addCommand.getPositionId()).thenReturn(positionId);
		when(editCommand.getPositionId()).thenReturn(positionId);
		when(employeeRepository.get(employeeId)).thenReturn(Optional.of(employee));
		when(employeeRepository.get(nonexistentEmployeeId)).thenReturn(Optional.empty());
		when(employeeRepository.getAll()).thenReturn(Collections.singletonList(employee));
		when(positionManager.isAvailable(positionId)).thenReturn(true);
	}

	@Test
	public void shouldValidateCommandOnAdd() {
		employeeManager.addEmployee(addCommand);

		verify(addCommand).validate(any());
	}


	@Test
	public void shouldVerifyPositionIsAvailableOnAdd() {
		employeeManager.addEmployee(addCommand);

		verify(positionManager).isAvailable(positionId);
	}

	@Test
	public void shouldThrowWhenAddingEmployeeWithUnavailablePosition() {
		when(positionManager.isAvailable(positionId)).thenReturn(false);

		Throwable thrown = catchThrowable(() -> employeeManager.addEmployee(addCommand));

		assertThat(thrown).isInstanceOf(PositionNotAvailableException.class);
	}

	@Test
	public void shouldCreateAndPersistEmployeeOnAdd() {
		employeeManager.addEmployee(addCommand);

		verify(employeeRepository).put(any());
	}

	@Test
	public void shouldFetchEmployeeOnGet() {
		employeeManager.getEmployee(employeeId);

		verify(employeeRepository).get(employeeId);
	}

	@Test
	public void shouldThrowIfEmployeeNotFound() {
		Throwable throwable = catchThrowable(() -> employeeManager.getEmployee(nonexistentEmployeeId));

		assertThat(throwable).isInstanceOf(EmployeeNotFoundException.class);
	}

	@Test
	public void shouldFetchEmployeesOnList() {
		employeeManager.listEmployees();

		verify(employeeRepository).getAll();
	}

	@Test
	public void shouldFilterOutUnavailableEmployeesOnList() {
		employeeManager.listEmployees();

		verify(employee).isAvailable();
	}

	@Test
	public void shouldValidateCommandOnEdit() {
		employeeManager.editEmployee(employeeId, editCommand);

		verify(editCommand).validate(any());
	}

	@Test
	public void shouldVerifyPositionIsAvailableOnEdit() {
		employeeManager.editEmployee(employeeId, editCommand);

		verify(positionManager).isAvailable(positionId);
	}

	@Test
	public void shouldFetchEmployeeOnEdit() {
		employeeManager.editEmployee(employeeId, editCommand);

		verify(employeeRepository).get(employeeId);
	}

	@Test
	public void shouldThrowWhenEditingNonexistentEmployee() {
		Throwable thrown = catchThrowable(() -> employeeManager.editEmployee(nonexistentEmployeeId, editCommand));

		assertThat(thrown).isInstanceOf(EmployeeNotFoundException.class);
	}

	@Test
	public void shouldEditEmployeeOnEdit() {
		employeeManager.editEmployee(employeeId, editCommand);

		verify(employee).edit(editCommand);
	}

	@Test
	public void shouldFetchEmployeeOnRemove() {
		employeeManager.removeEmployee(employeeId);

		verify(employeeRepository).get(employeeId);
	}

	@Test
	public void shouldThrowWhenRemovingNonexistentEmployee() {
		Throwable thrown = catchThrowable(() -> employeeManager.removeEmployee(nonexistentEmployeeId));

		assertThat(thrown).isInstanceOf(EmployeeNotFoundException.class);
	}

	@Test
	public void shouldRemoveEmployeeOnRemove() {
		employeeManager.removeEmployee(employeeId);

		verify(employee).remove();
	}

}
