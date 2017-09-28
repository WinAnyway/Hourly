package pl.przygudzki.hourly.shift;

import org.junit.Test;
import pl.przygudzki.hourly.employee.EmployeeConfiguration;
import pl.przygudzki.hourly.employee.EmployeeManager;
import pl.przygudzki.hourly.employee.EmployeePreparer;
import pl.przygudzki.hourly.position.PositionConfiguration;
import pl.przygudzki.hourly.position.PositionManager;
import pl.przygudzki.hourly.position.PositionPreparer;

class ShiftAssignment {

	private ShiftManager shiftManager = new ShiftConfiguration().shiftManager();
	private PositionManager positionManager = new PositionConfiguration().positionManager();
	private EmployeeManager employeeManager = new EmployeeConfiguration().employeeManager(positionManager);

	private ShiftPreparer givenShift = new ShiftPreparer(shiftManager);
	private PositionPreparer givenPosition = new PositionPreparer(positionManager);
	private EmployeePreparer givenEmployee = new EmployeePreparer(employeeManager, givenPosition);

	private ShiftAssignmentManager assignmentManager = new ShiftAssignmentConfiguration().shiftAssignmentManager();

	@Test
	public void shouldAssignAnEmployeeToShift() {
		// given a shift and an employee are present in the system
		givenShift.newShiftIsAdded();
		givenEmployee.newEmployeeIsAdded();

		// when we create shift assignment
//		assignmentManager.assign()

	}

}
