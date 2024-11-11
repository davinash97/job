package com.jobsphere.job.service;

import java.util.List;
import java.util.UUID;

import com.jobsphere.job.model.Job;

public interface JobService {
	Job create(String title, String description, Integer no_of_openings, String organization, List<String> skills);
	Job read(UUID id);
	List<Job> readAll();
	Job update(UUID id, String title, String description, Integer no_of_openings, String organization, List<String> skills);
	boolean delete(UUID id);
}