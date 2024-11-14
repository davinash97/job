package com.jobsphere.job.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobsphere.job.model.Job;
import com.jobsphere.job.model.Response;
import com.jobsphere.job.service.JobService;

@RestController
@RequestMapping("/job/{profile_id}")
public class JobControllerWithProfileId {

	private static final Logger log = LoggerFactory.getLogger(JobControllerWithProfileId.class);

	@Autowired
	private JobService service;

	@PostMapping
	public ResponseEntity<Response<?>> create(@PathVariable (required = false) UUID profile_id,
												@RequestBody (required=false) Job body,
												@RequestParam (required=false) String title,
												@RequestParam (required=false) String description,
												@RequestParam (required=false) Integer no_of_openings,
												@RequestParam (required=false) String organization,
												@RequestParam (required = false) List<String> skills) {
		try {
			if (body != null) {
				if (profile_id == null) {
					profile_id = body.getProfile_id();
				}

				if (title == null) {
					title = body.getTitle();
				}

				if (description == null) {
					description = body.getDescription();
				}

				if (no_of_openings == null) {
					no_of_openings = body.getNo_of_openings();
				}

				if (organization == null) {
					organization = body.getOrganization();
				}

				if (skills == null) {
					skills = body.getSkills();
				}
			}

			Job job = service.create(profile_id, title, description, no_of_openings, organization, skills);

			if (job == null)
				return ResponseEntity.internalServerError().body(new Response<>("bad", HttpStatus.NOT_FOUND.value(), job));
			log.debug("Created new job: {}", job.toString());
			return ResponseEntity.ok(new Response<>("success", HttpStatus.CREATED.value(), service.read(job.getJob_id())));
		} catch (Exception e) {
			Error.server(e.getMessage());
			return ResponseEntity.internalServerError().body(new Response<>("bad", HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	} 

	@GetMapping
	public ResponseEntity<Response<?>> readAllFromProfile(@PathVariable UUID profile_id) {
		try {
			log.debug("Fetched for {}", profile_id);
			List<Job> job = service.readFromOneProfile(profile_id);

			if(!job.isEmpty())
				return ResponseEntity.ok(new Response<>("success", HttpStatus.OK.value(), job));

			return ResponseEntity.ok(new Response<>("bad", HttpStatus.NOT_FOUND.value(), null));
		} catch (Exception e) {
			Error.server(e.getMessage());
			return ResponseEntity.internalServerError().body(new Response<>("bad", HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	@GetMapping("/{job_id}")
	public ResponseEntity<Response<?>> read(@PathVariable(required = false) UUID job_id) {
		try {
			log.debug("Fetching the job {}", job_id);
			return ResponseEntity.ok(new Response<>("success", HttpStatus.OK.value(), service.read(job_id)));
		} catch (Exception e) {
			Error.server(e.getMessage());
			return ResponseEntity.internalServerError().body(new Response<>("bad", HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	@PutMapping("/{job_id}")
	public ResponseEntity<Response<?>> update(@RequestBody (required=false) Job body,
												@PathVariable (required = false) UUID profile_id,
												@PathVariable (required = false) UUID job_id,
												@RequestParam (required = false) String title,
												@RequestParam (required = false) String description,
												@RequestParam (required = false) Integer no_of_openings,
												@RequestParam (required = false) String organization,
												@RequestParam (required = false) List<String> skills) {

		try {
			if (body != null) {
				if (profile_id == null) {
					profile_id = body.getProfile_id();
				}

				if (job_id == null) {
					job_id = body.getJob_id();
				}

				if (title == null) {
					title = body.getTitle();
				}

				if (description == null) {
					description = body.getDescription();
				}

				if (no_of_openings == null) {
					no_of_openings = body.getNo_of_openings();
				}

				if (organization == null) {
					organization = body.getOrganization();
				}

				if (skills == null) {
					skills = body.getSkills();
				}
			}

			Job job = service.update(profile_id, job_id, title, description, no_of_openings, organization, skills);

			if(job == null)
				return ResponseEntity.internalServerError().body(new Response<>("bad", HttpStatus.INTERNAL_SERVER_ERROR.value(), job));

			log.debug("Updated job: {}", job);
			return ResponseEntity.ok().body(new Response<>("success", HttpStatus.OK.value(), service.read(job.getJob_id())));
		} catch (Exception e) {
			Error.server(e.getMessage());
			return ResponseEntity.internalServerError().body(new Response<>("bad", HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	@DeleteMapping("/{job_id}")
	public ResponseEntity<Response<?>> delete(@PathVariable UUID job_id, @PathVariable UUID profile_id) {
		try {
			log.debug("Deleted Job: {}", job_id);
			return ResponseEntity.ok(new Response<>("success", HttpStatus.OK.value(), service.delete(profile_id, job_id)));
		} catch (Exception e) {
			Error.server(e.getMessage());
			return ResponseEntity.internalServerError().body(new Response<>("bad", HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	static class Error {
		public static void server(String message) {
			log.error("Error occurred at {}", message);
		}
	}
}
