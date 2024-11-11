package com.jobsphere.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobsphere.job.model.Job;

public interface JobRepository extends JpaRepository<Job, java.util.UUID> {
	
}
