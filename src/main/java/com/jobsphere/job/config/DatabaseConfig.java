package com.jobsphere.job.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

	@Value("${server.port}")
	public String port;

	@Value("${spring.datasource.url}")
	public String dbUrl;

	@Value("${spring.datasource.username}")
	public String dbUsername;

	@Value("${spring.datasource.password}")
	public String dbPassword;

}
