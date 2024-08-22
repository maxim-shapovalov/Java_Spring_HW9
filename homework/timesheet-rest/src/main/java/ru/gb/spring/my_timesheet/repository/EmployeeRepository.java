package ru.gb.spring.my_timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.spring.my_timesheet.model.Employee;
import ru.gb.spring.my_timesheet.model.Project;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
