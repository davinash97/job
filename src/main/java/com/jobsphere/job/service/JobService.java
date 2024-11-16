package com.jobsphere.job.service;

import java.util.List;
import java.util.UUID;

import com.jobsphere.job.model.Job;

public interface JobService {

	/*
	*	Interface for Job Service
	*/

	/*
	 * @params
	 * profile_id = UUID of profile from request
	 * title = title for job
	 * description = description for job
	 * no_of_openings = number of openings for job
	 * organization = organization name for job
	 * skills = Array of skills for job
	 * 
	 * @returns job response
	 */
	Job create(UUID profile_id, String title, String description, Integer no_of_openings, String organization, List<String> skills);

	/*
	 * @params
	 * job_id = of the job
	 * 
	 * @returns job response
	 */
	Job read(UUID job_id);

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
	Job update(UUID profile_id, UUID job_id, String title, String description, Integer no_of_openings, String organization, List<String> skills);

	/*
	 * @params
	 * profile_id = profile ID from request
	 * 
	 * @returns Array of posts from a specific profile
	 */
	List<Job> readFromOneProfile(UUID profile_id);

	/*
	 * @returns Array of job all posts
	 */
	List<Job> readAll();

	/*
	 * @params
	 * profile_id = UUID of profile
	 * job_id = UUID of job
	 * 
	 * @returns Boolean value
	 */
	Boolean delete(UUID profile_id, UUID job_id);

	/*
	 * @params
	 * profile_id = UUID of profile
	 * 
	 * @returns Boolean value if profile exist in Database
	 */
	Boolean profileExistsById(UUID profile_id);
}