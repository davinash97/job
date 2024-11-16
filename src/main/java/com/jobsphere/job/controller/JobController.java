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
@RequestMapping("/job")
public class JobController {

	/*
	*	Controller for Job
	*/

	private static final Logger log = LoggerFactory.getLogger(JobController.class);

	@Autowired
	private JobService service;

	/*
	 * @params
	 * profile_id = UUID of profile from request
	 * title = title for job
	 * description = description for job
	 * no_of_openings = number of openings for job
	 * organization = organization name for job
	 * skills = Array of skills for job
	 * 
	 * @returns newly corrected job response
	 */
	@PostMapping
	public ResponseEntity<Response<?>> create(@RequestBody (required=false) Job body,
											@RequestParam (required = false) UUID profile_id,
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

	/*
	 * @returns Array of job all posts
	 */
	@GetMapping
	public ResponseEntity<Response<?>> readAll() {
		try {
			log.debug("Fetching all jobs");
			return ResponseEntity.ok(new Response<>("success", HttpStatus.OK.value(), service.readAll()));
		} catch (Exception e) {
			Error.server(e.getMessage());
			return ResponseEntity.internalServerError().body(new Response<>("bad", HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	/*
	* @params
	* job_id = job ID of the Job
	* profile_id = UUID of profile from request
	* title = title for job
	* description = description for job
	* no_of_openings = number of openings for job
	* organization = organization name for job
	* skills = Array of skills for job
	* 
	* @returns updated job response
	*/
	@PutMapping
	public ResponseEntity<Response<?>> update(@RequestBody (required=false) Job body,
												@RequestParam (required = false) UUID profile_id,
												@RequestParam (required = false) UUID job_id,
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
				return ResponseEntity.internalServerError().body(new Response<>("bad", HttpStatus.NOT_FOUND.value(), job));

			log.debug("Updated job: {}", job);
			return ResponseEntity.ok().body(new Response<>("success", HttpStatus.OK.value(), service.read(job.getJob_id())));
		} catch (Exception e) {
			Error.server(e.getMessage());
			return ResponseEntity.internalServerError().body(new Response<>("bad", HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	/*
	 * @params
	 * profile_id = UUID of profile
	 * job_id = UUID of job
	 * 
	 * @returns deleted job response
	 */
	@DeleteMapping
	public ResponseEntity<Response<?>> delete(@RequestParam UUID job_id, @RequestParam UUID profile_id) {
		try {
			log.debug("Deleted Job: {}", job_id);
			return ResponseEntity.ok(new Response<>("success", HttpStatus.OK.value(), service.delete(profile_id, job_id)));
		} catch (Exception e) {
			Error.server(e.getMessage());
			return ResponseEntity.internalServerError().body(new Response<>("bad", HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	/*
	 * @params
	 * message = Error messaged passed from catch block
	 */
	static class Error {
		public static void server(String message) {
			log.error("Error occurred at {}", message);
		}
	}
}
