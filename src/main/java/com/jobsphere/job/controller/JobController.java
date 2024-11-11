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
import com.jobsphere.job.model.ResponseObject;
import com.jobsphere.job.service.JobService;

@RestController
@RequestMapping("/job")
public class JobController {

	private static final Logger log = LoggerFactory.getLogger(JobController.class);

	@Autowired
	private JobService service;

	@PostMapping
	public ResponseEntity<ResponseObject<?>> create(@RequestBody (required=false) Job body,
													@RequestParam (required=false) String title,
													@RequestParam (required=false) String description,
													@RequestParam (required=false) Integer no_of_openings,
													@RequestParam (required=false) String organization,
													@RequestParam (required = false) List<String> skills) {
		try {
			if (body != null) {
				if (title == null) {
					title = body.getTitle();
				}

				if (description == null) {
					description = body.getDescription();
				}

				if (no_of_openings == null) {
					no_of_openings = body.getNoOfOpenings();
				}

				if (organization == null) {
					organization = body.getOrganization();
				}

				if (skills == null) {
					skills = body.getSkills();
				}
			}

			Job job = service.create(title, description, no_of_openings, organization, skills);

			if (job == null)
				return ResponseEntity.internalServerError().body(new ResponseObject<>("bad", HttpStatus.NOT_FOUND.value(), job));
			log.debug("Created new job: {}", job.toString());
			return ResponseEntity.ok(new ResponseObject<>("success", HttpStatus.CREATED.value(), service.read(job.getId())));
		} catch (Exception e) {
			Error.server(e.getMessage());
			return ResponseEntity.internalServerError().body(new ResponseObject<>("bad", HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseObject<?>> read(@PathVariable UUID id) {
		try {
			log.debug("Fetched {}", id);
			return ResponseEntity.ok(new ResponseObject<>("success", HttpStatus.OK.value(), service.read(id)));
		} catch (Exception e) {
			Error.server(e.getMessage());
			return ResponseEntity.internalServerError().body(new ResponseObject<>("bad", HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	@GetMapping(value = "/all")
	public ResponseEntity<ResponseObject<?>> readAll() {
		try {
			log.debug("Fetching all jobs");
			return ResponseEntity.ok(new ResponseObject<>("success", HttpStatus.OK.value(), service.readAll()));
		} catch (Exception e) {
			Error.server(e.getMessage());
			return ResponseEntity.internalServerError().body(new ResponseObject<>("bad", HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseObject<?>> update(@RequestBody (required=false) Job body,
													@PathVariable (required = false) UUID id,
													@RequestParam (required = false) String title,
													@RequestParam (required = false) String description,
													@RequestParam (required = false) Integer no_of_openings,
													@RequestParam (required = false) String organization,
													@RequestParam (required = false) List<String> skills) {

		try {
			if (body != null) {
				if (id == null) {
					id = body.getId();
				}

				if (title == null) {
					title = body.getTitle();
				}

				if (description == null) {
					description = body.getDescription();
				}

				if (no_of_openings == null) {
					no_of_openings = body.getNoOfOpenings();
				}

				if (organization == null) {
					organization = body.getOrganization();
				}

				if (skills == null) {
					skills = body.getSkills();
				}
			}

			Job job = service.update(id, title, description, no_of_openings, organization, skills);

			if(job == null)
				return ResponseEntity.internalServerError().body(new ResponseObject<>("bad", HttpStatus.INTERNAL_SERVER_ERROR.value(), job));

			log.debug("Updated job: {}", job);
			return ResponseEntity.ok().body(new ResponseObject<>("success", HttpStatus.OK.value(), service.read(job.getId())));
		} catch (Exception e) {
			Error.server(e.getMessage());
			return ResponseEntity.internalServerError().body(new ResponseObject<>("bad", HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseObject<?>> delete(@PathVariable UUID id) {
		try {
			log.debug("Deleted Job: {}", id);
			return ResponseEntity.ok(new ResponseObject<>("success", HttpStatus.OK.value(), service.delete(id)));
		} catch (Exception e) {
			Error.server(e.getMessage());
			return ResponseEntity.internalServerError().body(new ResponseObject<>("bad", HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

	static class Error {
		public static void server(String message) {
			log.error("Error occurred at {}", message);
		}
	}
}
