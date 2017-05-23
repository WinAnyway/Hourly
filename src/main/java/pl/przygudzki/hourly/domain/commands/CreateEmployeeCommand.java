package pl.przygudzki.hourly.domain.commands;

import pl.przygudzki.hourly.domain.EmployeeId;
import pl.przygudzki.hourly.domain.Position;

public class CreateEmployeeCommand {

    private EmployeeId employeeId;
    private Position position;
    private String firstName;
    private String lastName;

    public EmployeeId getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(EmployeeId employeeId) {
        this.employeeId = employeeId;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
