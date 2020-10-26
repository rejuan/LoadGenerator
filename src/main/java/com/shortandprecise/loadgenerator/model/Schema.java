package com.shortandprecise.loadgenerator.model;

import java.util.List;

/**
 * LoadGenerator Schema
 */
public class Schema {
	private List<Request> requests;

	public Schema(List<Request> requests) {
		this.requests = requests;
	}

	public List<Request> getRequests() {
		return requests;
	}

	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}
}
