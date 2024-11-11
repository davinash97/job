package com.jobsphere.job.model;

import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public class ResponseObject<T> {
	public String status;

	public int code;

	public T result;

	public ResponseObject(String status, int code, T result) {
		this.status = status;
		this.code = code;
		this.result = result;
	}
}
