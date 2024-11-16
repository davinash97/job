package com.jobsphere.job.model;

import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public class Response<T> {

	/*
	*	response status
	*/
	public String status;

	/*
	*	response code
	*/
	public int code;

	/*
	*	response result
	*/
	public T result;

	/*
	*	Response model constructor
	*/
	public Response(String status, int code, T result) {
		this.status = status;
		this.code = code;
		this.result = result;
	}

	/*
	*	get result method
	*/
	public T getResult() {
		return this.result;
	}
}
