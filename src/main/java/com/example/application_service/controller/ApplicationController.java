package com.example.application_service.controller;

import com.example.application_service.entity.Application;
import com.example.application_service.service.ApplicationService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    // Create application
    @PostMapping
    public Application createApplication(@RequestBody Application application) {
        return applicationService.createApplication(application);
    }

    // Get all applications
    @GetMapping
    public List<Application> getAllApplications() {
        return applicationService.getAllApplications();
    }

    // Get application by ID
    @GetMapping("/{id}")
    public Application getApplicationById(@PathVariable Long id) {
        return applicationService.getApplicationById(id);
    }

    // Update application
    @PutMapping("/{id}")
    public Application updateApplication(
            @PathVariable Long id,
            @RequestBody Application application) {
        return applicationService.updateApplication(id, application);
    }

    // Delete application
    @DeleteMapping("/{id}")
    public String deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return "Application deleted successfully";
    }
}