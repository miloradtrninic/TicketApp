package com.msmisa.TicketApp.dto.creation;

import java.sql.Date;
import java.util.List;
import com.msmisa.TicketApp.beans.Hall;
import com.msmisa.TicketApp.beans.Projection;
import com.msmisa.TicketApp.dto.ForeignKeyDTO;

public class TerminCreationDTO {

	@ForeignKeyDTO(clazzFK=Projection.class)
	private Integer projectionId;
	
	@ForeignKeyDTO(clazzFK=Hall.class)
	private List<Integer> hallIds;
	private Date date;
	private String time;
	private Integer price;
	public Integer getProjectionId() {
		return projectionId;
	}
	public void setProjectionId(Integer projectionId) {
		this.projectionId = projectionId;
	}
	public List<Integer> getHallIds() {
		return hallIds;
	}
	public void setHallIds(List<Integer> hallIds) {
		this.hallIds = hallIds;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	
	
}
