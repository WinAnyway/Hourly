package pl.przygudzki.hourly;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import pl.przygudzki.hourly.api.EmployeeManager;
import pl.przygudzki.hourly.api.StandardEmployeeManager;
import pl.przygudzki.hourly.domain.Employee;
import pl.przygudzki.hourly.domain.EmployeeId;
import pl.przygudzki.hourly.domain.EmployeeRepository;
import pl.przygudzki.hourly.domain.Position;
import pl.przygudzki.hourly.domain.commands.CreateEmployeeCommand;
import pl.przygudzki.hourly.domain.commands.InvalidCommandException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StandardEmployeeManagerTest {

    private EmployeeRepository employeeRepository;
    private EmployeeManager employeeManager;

    @Before
    public void setUp() {
        employeeRepository = mock(EmployeeRepository.class);
        employeeManager = new StandardEmployeeManager(employeeRepository);
    }

    @Test
    public void shouldCreateEmployee() {
        CreateEmployeeCommand cmd = new CreateEmployeeCommand();
        cmd.setEmployeeId(new EmployeeId(1L));
        cmd.setPosition(new Position("Position"));
        cmd.setFirstName("John");
        cmd.setLastName("Doe");

        employeeManager.createEmployee(cmd);

        verify(employeeRepository, times(1)).put(new Employee(cmd));
    }

    @Test(expected = InvalidCommandException.class)
    public void shouldNotCreateEmployeeWhenEmployeeIdIsNull() {
        CreateEmployeeCommand cmd = new CreateEmployeeCommand();
        cmd.setPosition(new Position("Position"));
        cmd.setFirstName("John");
        cmd.setLastName("Doe");

        employeeManager.createEmployee(cmd);
    }

    @Test(expected = InvalidCommandException.class)
    public void shouldNotCreateEmployeeWhenPositionIsNull() {
        CreateEmployeeCommand cmd = new CreateEmployeeCommand();
        cmd.setEmployeeId(new EmployeeId(1L));
        cmd.setFirstName("John");
        cmd.setLastName("Doe");

        employeeManager.createEmployee(cmd);
    }

    @Test(expected = InvalidCommandException.class)
    public void shouldNotCreateEmployeeWhenPositionIsEmpty() {
        CreateEmployeeCommand cmd = new CreateEmployeeCommand();
        cmd.setEmployeeId(new EmployeeId(1L));
        cmd.setPosition(new Position(" "));
        cmd.setFirstName("John");
        cmd.setLastName("Doe");

        employeeManager.createEmployee(cmd);
    }

    @Test(expected = InvalidCommandException.class)
    public void shouldNotCreateEmployeeWhenFirstNameIsNull() {
        CreateEmployeeCommand cmd = new CreateEmployeeCommand();
        cmd.setEmployeeId(new EmployeeId(1L));
        cmd.setPosition(new Position("Position"));
        cmd.setLastName("Doe");

        employeeManager.createEmployee(cmd);
    }

    @Test(expected = InvalidCommandException.class)
    public void shouldNotCreateEmployeeWhenFirstNameIsEmpty() {
        CreateEmployeeCommand cmd = new CreateEmployeeCommand();
        cmd.setEmployeeId(new EmployeeId(1L));
        cmd.setPosition(new Position("Position"));
        cmd.setFirstName("");
        cmd.setLastName("Doe");

        employeeManager.createEmployee(cmd);
    }

    @Test(expected = InvalidCommandException.class)
    public void shouldNotCreateEmployeeWhenLastNameIsNull() {
        CreateEmployeeCommand cmd = new CreateEmployeeCommand();
        cmd.setEmployeeId(new EmployeeId(1L));
        cmd.setPosition(new Position("Position"));
        cmd.setFirstName("John");

        employeeManager.createEmployee(cmd);
    }

    @Test(expected = InvalidCommandException.class)
    public void shouldNotCreateEmployeeWhenLastNameIsEmpty() {
        CreateEmployeeCommand cmd = new CreateEmployeeCommand();
        cmd.setEmployeeId(new EmployeeId(1L));
        cmd.setPosition(new Position("Position"));
        cmd.setFirstName("John");
        cmd.setLastName("");

        employeeManager.createEmployee(cmd);
    }
}
