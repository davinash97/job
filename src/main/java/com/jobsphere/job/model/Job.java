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
	private UUID id;

	private String title;

	private String description;

	private List<String> skills = new ArrayList<>();

	private Integer no_of_openings = (Integer) 1;

	private String organization;

	public Job(){}

	public Job(String title, String description, Integer no_of_openings, String organization, List<String> skills) {
		this.title = title;
		this.description = description;
		this.no_of_openings = no_of_openings;
		this.organization = organization;
		this.skills = skills;
	}
	
	public UUID getId() {
		return id;
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

	public Integer getNoOfOpenings() {
		return no_of_openings;
	}

	public void setNoOfOpenings(Integer no_of_openings) {
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
		return "id:\t" + this.id + " title:\t" + this.title;
	}
}
