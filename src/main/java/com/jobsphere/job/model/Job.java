package com.jobsphere.job.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "job") // Table name in database
public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)	// Auto generated at database level
	private UUID job_id; // ID in UUID type for Database

	private UUID profile_id; // ID in UUID type of Profile

	private String title; // title of job

	private String description;	// description of job

	private List<String> skills = new ArrayList<>(); // List of skills

	private Integer no_of_openings = 1; // No of openings initialized to 1 as it can't be zero :)

	private String organization; // Name of Organization posting this

	public Job(){} // Blank constructor for bean

	// default constructor for job
	/*
	 * @params
	 * profile_id = UUID recieved from request
	 * title = title for the job post in String format
	 * description = description for the job post in String format
	 * no_of_openings = Number of openings which can't be 0 so initializing with 1
	 * organization = Job organization
	 * skills = Array of skills needed for the job
	 */
	public Job(UUID profile_id, String title, String description, Integer no_of_openings, String organization, List<String> skills) {
		this.profile_id = profile_id;
		this.title = title;
		this.description = description;
		this.no_of_openings = no_of_openings;
		this.organization = organization;
		this.skills = skills;
	}

	/*
	*	@returns profile ID of the job poster
	*/ 

	public UUID getProfile_id() {
		return profile_id;
	}
	
	/*
	*	@returns job ID of job
	*/
	public UUID getJob_id() {
		return job_id;
	}

	/*
	*	@returns title of the job
	*/
	public String getTitle() {
		return title;
	}

	/*
	*	@params
	*	title = set title for the job
	*/
	public void setTitle(String title) {
		this.title = title;
	}

	/*
	*	@returns description of the job
	*/
	public String getDescription() {
		return description;
	}

	/*
	 * @params
	 * description = set description of the job
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * @returns skills from the post
	 */
	public List<String> getSkills() {
		return skills;
	}

	/*
	 * @params
	 * skill = set array of skills for job
	 */
	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	/*
	 * @returns returns number of openings from job which is greater than 1
	 */
	public Integer getNo_of_openings() {
		return no_of_openings;
	}

	/*
	 * @params
	 * no_of_openings = set number of openings greater than 1
	 */
	public void setNo_of_openings(Integer no_of_openings) {
		if(no_of_openings < 1) no_of_openings = 1;
		this.no_of_openings = no_of_openings;
	}

	/*
	 * @returns organization name from job
	 */
	public String getOrganization() {
		return organization;
	}

	/*
	 * @params
	 * organization = set organization of job
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/*
	 * @returns job as string
	 */
	@Override
	public String toString() {
		return "job_id " + this.job_id +
				"profile_id " + this.profile_id +
				"title" + this.title +
				"description" + this.description +
				"skills" + this.skills +
				"no_of_openings" + this.no_of_openings +
				"organization" + this.organization;
	}
}
