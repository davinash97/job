package com.jobsphere.job.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobsphere.job.model.Job;

public interface JobRepository extends JpaRepository<Job, java.util.UUID> {
	
	/*
	 * @params
	 * profile_id = profile id to check if table has the profile id
	 */
	@Query("SELECT j FROM Job j WHERE j.profile_id = :profile_id")
	List<Job> findAllJobsByProfileId(@Param("profile_id") UUID profile_id);

}
