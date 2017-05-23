package pl.przygudzki.hourly.api;

import pl.przygudzki.hourly.domain.commands.CreateEmployeeCommand;

public interface EmployeeManager {

    void createEmployee(CreateEmployeeCommand cmd);

}
