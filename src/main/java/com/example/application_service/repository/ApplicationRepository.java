package com.example.application_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application_service.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> 
{
    
}
