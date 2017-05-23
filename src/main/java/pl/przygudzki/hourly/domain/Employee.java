package pl.przygudzki.hourly.domain;

import pl.przygudzki.hourly.domain.commands.CreateEmployeeCommand;

public class Employee {

    private EmployeeId id;
    private Position position;
    private String firstName;
    private String lastName;


    public Employee(CreateEmployeeCommand cmd) {
        this.id = cmd.getEmployeeId();
        this.position = cmd.getPosition();
        this.firstName = cmd.getFirstName();
        this.lastName = cmd.getLastName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (!id.equals(employee.id)) return false;
        if (!position.equals(employee.position)) return false;
        if (!firstName.equals(employee.firstName)) return false;
        return lastName.equals(employee.lastName);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + position.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        return result;
    }
}
