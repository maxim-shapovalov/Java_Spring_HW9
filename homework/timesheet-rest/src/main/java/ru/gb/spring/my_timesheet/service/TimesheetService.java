package ru.gb.spring.my_timesheet.service;

import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.spring.my_timesheet.aspect.Recover;
import ru.gb.spring.my_timesheet.aspect.Timer;
import ru.gb.spring.my_timesheet.model.Project;
import ru.gb.spring.my_timesheet.model.Timesheet;
import ru.gb.spring.my_timesheet.repository.ProjectRepository;
import ru.gb.spring.my_timesheet.repository.TimesheetRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service // то же самое, что и Component, это больше метка для нас
@Timer(level = Level.TRACE)
public class TimesheetService {

    private final TimesheetRepository timesheetRepository;
    private final ProjectRepository projectRepository;

    public TimesheetService(TimesheetService timesheetService) {
        this(timesheetService.timesheetRepository, timesheetService.projectRepository);
    }
    @Autowired
    public TimesheetService(TimesheetRepository timesheetRepository, ProjectRepository projectRepository) {
        this.timesheetRepository = timesheetRepository;
        this.projectRepository = projectRepository;
    }

//    @Timer
    public Optional<Timesheet> findById(Long id) {
        return timesheetRepository.findById(id);
    }

    public List<Timesheet> findAll() {
        return timesheetRepository.findAll();
    }

    public List<Timesheet> findByCreatedAfter(LocalDate date){
        return timesheetRepository.findByCreatedAtAfter(date);
//        return timesheets.stream().filter(elem -> (elem.getCreatedAt().compareTo(date.atStartOfDay()) > 0 )).toList();
    }

    public List<Timesheet> findByCreatedBefore(LocalDate date){
        return timesheetRepository.findByCreatedAtBefore(date);
//        return timesheets.stream().filter(elem -> (elem.getCreatedAt().compareTo(date.atStartOfDay()) < 0 )).toList();
    }

    public Timesheet create(Timesheet timesheet) {
        if (Objects.isNull(timesheet.getProject().getId())) {
            throw new IllegalArgumentException("projectId must not be null");
        }

        if (projectRepository.findById(timesheet.getProject().getId()).isEmpty()) {
            throw new NoSuchElementException("Project with id " + timesheet.getProject().getId() + " does not exists");
        }
//        timesheet.setCreatedAt(LocalDateTime.now().withNano(0));
//        timesheet.setCreatedAt(LocalDateTime.now().withNano(0));
        timesheet.setCreatedAt(LocalDate.now());
        return timesheetRepository.save(timesheet);
    }

//    @Timer(enabled = false) // выключение аннотации без ее комментирования

    public void delete(Long id) {
        timesheetRepository.deleteById(id);
    }

    public Optional<Project> getProjectById(Long id){
        return projectRepository.findById(id);
    }

}
