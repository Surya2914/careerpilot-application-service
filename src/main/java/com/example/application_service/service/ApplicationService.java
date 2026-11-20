
package com.example.application_service.service;

import com.example.application_service.entity.Application;
import com.example.application_service.repository.ApplicationRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final RestTemplate restTemplate;

    public ApplicationService(
            ApplicationRepository applicationRepository,
            RestTemplate restTemplate) {

        this.applicationRepository = applicationRepository;
        this.restTemplate = restTemplate;
    }

    // Create application
    public Application createApplication(Application application) {

        application.setId(null);
        application.setStatus("APPLIED");

        if (application.getAppliedAt() == null) {
            application.setAppliedAt(
                java.time.LocalDateTime.now()
            );
        }

        Application savedApplication =
                applicationRepository.save(application);

        // Create notification
        try {
            Map<String, Object> notification = new HashMap<>();

            notification.put("userId", savedApplication.getUserId());

            notification.put(
                "message",
                "Your job application has been submitted successfully."
            );

            notification.put("type", "APPLICATION");

            // notification.put("read", false);

            // notification.put(
            //     "createdAt",
            //     java.time.LocalDateTime.now().toString()
            // );

            String notificationUrl =
                    "http://localhost:8084/api/notifications";

            restTemplate.postForEntity(
                notificationUrl,
                notification,
                String.class
            );

            System.out.println("Notification created successfully!");

        } catch (Exception e) {

            System.out.println(
                "Notification creation failed: " + e.getMessage()
            );
        }

        return savedApplication;
    }

    // Get all applications
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    // Get application by ID
    public Application getApplicationById(Long id) {
        return applicationRepository.findById(id).orElse(null);
    }

    // Update application
    public Application updateApplication(
            Long id,
            Application updatedApplication) {

        Application application =
                applicationRepository.findById(id).orElse(null);

        if (application != null) {

            application.setUserId(updatedApplication.getUserId());
            application.setJobId(updatedApplication.getJobId());
            application.setCoverLetter(updatedApplication.getCoverLetter());
            application.setResumeUrl(updatedApplication.getResumeUrl());
            application.setStatus(updatedApplication.getStatus());

            return applicationRepository.save(application);
        }

        return null;
    }

    // Delete application
    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }
}
