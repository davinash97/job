package com.jobsphere.job.service.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobsphere.job.model.Job;
import com.jobsphere.job.repository.JobRepository;
import com.jobsphere.job.service.JobService;

@Service
public class JobServiceImpl implements JobService {

	private static final Logger log = LoggerFactory.getLogger(JobService.class);

	@Autowired
	private JobRepository repository;

	@Override
	public Job create(String title, String description, Integer no_of_openings, String organization, List<String> skills) {
		try {
			Job job = new Job(title, description, no_of_openings, organization, skills);

			repository.save(job);

			if(repository.existsById(job.getId())) {
				log.debug(job.toString());
				return read(job.getId());
			}
			return null;
		} catch (Exception e) {
			Error.server(e.getMessage());
			return null;
		}
	}

	@Override
	public List<Job> readAll() {
		log.debug("Fetched all jobs");
		return repository.findAll();
	}

	@Override
	public Job read(UUID id) {
		try {
			Job job = repository.findById(id).orElse(null);
			if(job!=null) log.debug(job.toString());
			return job;
		} catch(Exception e) {
			Error.server(e.getMessage());
			return null;
		}
	}

	@Override
	public Job update(UUID id, String title, String description, Integer no_of_openings, String organization, List<String> skills) {

		try {
			Job job = read(id);

			if (job != null) {
				if (!title.isBlank())
					job.setTitle(title);

				if (!description.isBlank())
					job.setDescription(description);

				if (!organization.isBlank())
					job.setOrganization(organization);

				if (!Integer.toString(no_of_openings).isBlank())
					job.setNoOfOpenings(no_of_openings);

				if (skills != null)
					job.setSkills(skills);

				repository.save(job);
				log.debug(job.toString());
				return read(job.getId());
			}
			return null;
		} catch (Exception e) {
			Error.server(e.getMessage());
			return null;
		}
	}

	@Override
	public boolean delete(UUID id) {
		try {
			repository.deleteById(id);
			log.debug("Deleted");
			return !repository.existsById(id);
		} catch (Exception e) {
			Error.server(e.getMessage());
			return false;
		}
	}

	static class Error {
		public static void server(String message) {
			log.error(message);
		}
	}
}

