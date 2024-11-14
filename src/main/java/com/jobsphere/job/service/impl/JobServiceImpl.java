package com.jobsphere.job.service.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.jobsphere.job.model.Job;
import com.jobsphere.job.model.Response;
import com.jobsphere.job.repository.JobRepository;
import com.jobsphere.job.service.JobService;

@Service
public class JobServiceImpl implements JobService {

	private static final Logger log = LoggerFactory.getLogger(JobService.class);

	@Autowired
	private RestTemplate restTemplate;

	@Value("${profile-service.url}")
	private String profileServiceUrl;

	@Autowired
	private JobRepository repository;

	@Override
	public Boolean profileExistsById(UUID profile_id) {
		try {
			String url = profileServiceUrl + "/profile/" + profile_id + "/exists";
			
			Response<Boolean> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Response<Boolean>>() {}).getBody();
			
			if (response == null || response.getResult() == null) {
				log.error("Response is null for profile_id: {}", profile_id);
				return false;
			}
	
			Boolean exists = response.getResult();
			log.debug("profile_id: {}, exists: {}", profile_id, exists);
	
			return exists;
		} catch (RestClientException e) {
			log.error("Error occurred while checking profile existence for profile_id: {}. Error: {}", profile_id, e.getMessage(), e);
			Error.server("Unable to check profile existence for profile_id: " + profile_id);
			return false;
		}
	}

	@Override
	public Job create(UUID profile_id, String title, String description, Integer no_of_openings, String organization, List<String> skills) {
		try {
			if(!profileExistsById(profile_id)) return null;

			Job job = new Job(profile_id, title, description, no_of_openings, organization, skills);

			repository.save(job);

			if(repository.existsById(job.getJob_id())) {
				log.debug(job.toString());
				return read(job.getJob_id());
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
	public Job read(UUID job_id) {
		try {
			Job job = (repository.existsById(job_id)) ? repository.findById(job_id).orElse(null): null;
			if(job!=null) log.debug(job.toString());
			return job;
		} catch(Exception e) {
			Error.server(e.getMessage());
			return null;
		}
	}

	@Override
	public List<Job> readFromOneProfile(UUID profile_id) {
		try {
			List<Job> list = repository.findAllJobsByProfileId(profile_id);
			if(!list.isEmpty()) return list;
			return null;
		} catch (Exception e) {
			Error.server(e.getMessage());
			return null;
		}
	}

	@Override
	public Job update(UUID profile_id, UUID job_id, String title, String description, Integer no_of_openings, String organization, List<String> skills) {

		try {
			Job job = (repository.existsById(job_id)) ? read(job_id): null;

			if (job != null && profileExistsById(profile_id) && profile_id.equals(job.getProfile_id())) {

				if (!title.isBlank())
					job.setTitle(title);

				if (!description.isBlank())
					job.setDescription(description);

				if (!organization.isBlank())
					job.setOrganization(organization);

				if (!Integer.toString(no_of_openings).isBlank())
					job.setNo_of_openings(no_of_openings);

				if (skills != null)
					job.setSkills(skills);

				repository.save(job);
				log.debug(job.toString());
				return read(job.getJob_id());
			}
			return null;
		} catch (Exception e) {
			Error.server(e.getMessage());
			return null;
		}
	}

	@Override
	public Boolean delete(UUID profile_id, UUID job_id) {
		try {
			Job job = (repository.existsById(job_id)) ? read(job_id): null;
			if(job == null) return false;

			if(profileExistsById(profile_id) && profile_id.equals(job.getProfile_id())) {
				repository.deleteById(job_id);
				log.debug("Deleted");
				return !repository.existsById(job_id);
			} return false;
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

