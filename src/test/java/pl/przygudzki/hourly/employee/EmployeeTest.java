package pl.przygudzki.hourly.employee;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.przygudzki.hourly.employee.dto.*;
import pl.przygudzki.hourly.position.PositionId;
import pl.przygudzki.hourly.position.PositionManager;
import pl.przygudzki.hourly.position.dto.PositionDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeTest {

	private Employee employee;

	@Mock
	private PositionManager positionManager;

	private EmployeeCommandPreparer given = new EmployeeCommandPreparer();
	private AddEmployeeCommand createCommand;
	private PositionId positionId;

	@Mock
	private PositionDto positionDto;

	@Before
	public void setUp() throws Exception {
		createCommand = given.validAddEmployeeCommand(positionId);
		employee = Employee.create(createCommand);

		when(positionManager.getPosition(positionId)).thenReturn(positionDto);
		when(positionDto.getId()).thenReturn(positionId);
	}

	@Test
	public void shouldGenerateUniqueIds() {
		Employee employee1 = Employee.create(createCommand);
		Employee employee2 = Employee.create(createCommand);

		EmployeeDto employeeDto1 = buildEmployeeDto(employee1);
		EmployeeDto employeeDto2 = buildEmployeeDto(employee2);

		assertThat(employeeDto1.getId()).isNotEqualTo(employeeDto2.getId());
	}

	@Test
	public void shouldStoreData() {
		employee = Employee.create(createCommand);

		EmployeeDto employeeDto = buildEmployeeDto(employee);
		assertThat(employeeDto.getPosition().getId()).isEqualTo(createCommand.getPositionId());
		assertThat(employeeDto.getFirstName()).isEqualTo(createCommand.getFirstName());
		assertThat(employeeDto.getLastName()).isEqualTo(createCommand.getLastName());
	}

	@Test
	public void shouldAssignAvailableStatusOnCreation() {
		Employee employee = Employee.create(createCommand);

		EmployeeDto employeeDto = buildEmployeeDto(employee);
		assertThat(employeeDto.getStatus()).isEqualTo("AVAILABLE");
	}

	@Test
	public void shouldReturnIsAvailableAfterCreation() {
		assertThat(employee.isAvailable()).isTrue();
	}

	@Test
	public void shouldStoreNewDataOnEdit() {
		EditEmployeeCommand command = given.validEditEmployeeCommand(positionId);

		employee.edit(command);

		EmployeeDto employeeDto = buildEmployeeDto(employee);
		assertThat(employeeDto.getPosition().getId()).isEqualTo(createCommand.getPositionId());
		assertThat(employeeDto.getFirstName()).isEqualTo(command.getFirstName());
		assertThat(employeeDto.getLastName()).isEqualTo(command.getLastName());
	}

	@Test
	public void shouldAssignRemovedStatusOnRemoval() {
		employee.remove();

		EmployeeDto employeeDto = buildEmployeeDto(employee);
		assertThat(employeeDto.getStatus()).isEqualTo("REMOVED");
	}

	private EmployeeDto buildEmployeeDto(Employee employee) {
		EmployeeDtoBuilder builder = new EmployeeDtoBuilder(positionManager);
		employee.export(builder);
		return builder.build();
	}

}
