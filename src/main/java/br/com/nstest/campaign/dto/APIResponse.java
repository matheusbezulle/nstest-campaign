package br.com.nstest.campaign.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse {
	
	private Boolean success;
	private String message;
	private Object body;
	
	public APIResponse() { }
	
	public APIResponse(Boolean success, String message, Object body) {
		this.success = success;
		this.message = message;
		this.body = body;
	}
	
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getBody() {
		return body;
	}
	public void setBody(Object body) {
		this.body = body;
	}
	
}
