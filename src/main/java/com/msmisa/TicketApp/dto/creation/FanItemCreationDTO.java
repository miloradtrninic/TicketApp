package com.msmisa.TicketApp.dto.creation;

import com.msmisa.TicketApp.beans.FanZone;
import com.msmisa.TicketApp.beans.Projection;
import com.msmisa.TicketApp.dto.ForeignKeyDTO;

public class FanItemCreationDTO {
	private String name;
	private String imagePath;
	private String description;
	@ForeignKeyDTO(clazzFK=Projection.class)
	private Integer projectionId;
	
	@ForeignKeyDTO(clazzFK=FanZone.class)
	private Integer fanzoneId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getProjectionId() {
		return projectionId;
	}
	public void setProjectionId(Integer projectionId) {
		this.projectionId = projectionId;
	}
	public Integer getFanzoneId() {
		return fanzoneId;
	}
	public void setFanzoneId(Integer fanzoneId) {
		this.fanzoneId = fanzoneId;
	}
	
	
}
