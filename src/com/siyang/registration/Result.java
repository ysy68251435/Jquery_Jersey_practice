package com.siyang.registration;

public class Result {
	
	private int status;
	private String error;
	
	public Result(){
		
	}
	
	public Result(int status){
		this.status=status;
	}
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
