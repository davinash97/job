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
@Table(name = "job")
public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID job_id;

	private UUID profile_id;

	private String title;

	private String description;

	private List<String> skills = new ArrayList<>();

	private Integer no_of_openings = 1;

	private String organization;

	public Job(){}

	public Job(UUID profile_id, String title, String description, Integer no_of_openings, String organization, List<String> skills) {
		this.profile_id = profile_id;
		this.title = title;
		this.description = description;
		this.no_of_openings = no_of_openings;
		this.organization = organization;
		this.skills = skills;
	}

	public UUID getProfile_id() {
		return profile_id;
	}
	
	public UUID getJob_id() {
		return job_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getSkills() {
		return skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	public Integer getNo_of_openings() {
		return no_of_openings;
	}

	public void setNo_of_openings(Integer no_of_openings) {
		this.no_of_openings = no_of_openings;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

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
