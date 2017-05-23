package pl.przygudzki.hourly.domain.commands;

import pl.przygudzki.hourly.domain.EmployeeId;
import pl.przygudzki.hourly.domain.Position;

public class CreateEmployeeCommand implements Validatable {

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

    @Override
    public void validate(ValidationErrors errors) {
        validateEmployeeId(errors);
        validatePosition(errors);
        validateFirstName(errors);
        validateLastName(errors);
    }

    private void validateLastName(ValidationErrors errors) {
        if(isEmpty(lastName))
            errors.add("lastName", "This field needs to be filled");
    }

    private void validateFirstName(ValidationErrors errors) {
        if(isEmpty(firstName))
            errors.add("firstName", "This field needs to be filled");
    }

    private void validatePosition(ValidationErrors errors) {
        if (position == null)
            errors.add("position", "This field needs to be filled");
        else if (isEmpty(position.getPosition()))
            errors.add("position", "This field needs to be filled");
    }

    private void validateEmployeeId(ValidationErrors errors) {
        if (employeeId == null)
            errors.add("employeeId", "This field needs to be filled");
        else if (employeeId.getId() == null)
            errors.add("employeeId", "This field needs to be filled");
    }
}
