package ru.gb.spring.my_timesheet.service;

import org.springframework.stereotype.Service;
import ru.gb.spring.my_timesheet.aspect.Recover;
import ru.gb.spring.my_timesheet.model.Project;
import ru.gb.spring.my_timesheet.model.Timesheet;
import ru.gb.spring.my_timesheet.repository.ProjectRepository;
import ru.gb.spring.my_timesheet.repository.TimesheetRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TimesheetRepository timesheetRepository;

    public ProjectService(ProjectRepository projectRepository, TimesheetRepository timesheetRepository) {
        this.projectRepository = projectRepository;
        this.timesheetRepository = timesheetRepository;
    }

    public Optional<Project> findById(Long id){
        return projectRepository.findById(id);
    }

    public List<Project> findAll(){
        return projectRepository.findAll();
    }

    @Recover
    public Project create(Project project){
        return projectRepository.save(project);
    }

    public void delete(Long id){
        projectRepository.deleteById(id);
    }

    public List<Timesheet> getTimesheetsByProjectId(Long id){
        List<Timesheet> timesheetsByProjectId = new ArrayList<>();
        for (Timesheet timesheet : timesheetRepository.findAll()) {
            if(timesheet.getProject().getId().equals(id)){
                timesheetsByProjectId.add(timesheet);
            }
        }
        return timesheetsByProjectId;
    }
}
