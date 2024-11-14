package com.jobsphere.job.model;

import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public class Response<T> {
	public String status;

	public int code;

	public T result;

	public Response(String status, int code, T result) {
		this.status = status;
		this.code = code;
		this.result = result;
	}

	public T getResult() {
		return this.result;
	}
}
