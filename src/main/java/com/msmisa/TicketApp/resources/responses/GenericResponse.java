package com.msmisa.TicketApp.resources.responses;

import java.util.List;

public class GenericResponse<Entity> {
	private List<Entity> entity;
	private Integer results;
	
	public List<Entity> getEntity() {
		return entity;
	}
	public void setEntity(List<Entity> entity) {
		this.entity = entity;
	}
	public Integer getResults() {
		return results;
	}
	public void setResults(Integer results) {
		this.results = results;
	}
	
	
}
