package com.jobsphere.job.service;

import java.util.List;
import java.util.UUID;

import com.jobsphere.job.model.Job;

public interface JobService {

	// Returns Job
	Job create(UUID profile_id, String title, String description, Integer no_of_openings, String organization, List<String> skills);
	Job read(UUID job_id);
	Job update(UUID profile_id, UUID job_id, String title, String description, Integer no_of_openings, String organization, List<String> skills);

	// Returns Array of jobs
	List<Job> readFromOneProfile(UUID profile_id);
	List<Job> readAll();

	// Returns Boolean value
	Boolean delete(UUID profile_id, UUID job_id);
	Boolean profileExistsById(UUID profile_id);
}