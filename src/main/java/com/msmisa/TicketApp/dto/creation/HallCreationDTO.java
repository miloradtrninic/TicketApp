package com.msmisa.TicketApp.dto.creation;

import java.util.List;

import com.msmisa.TicketApp.beans.Auditorium;
import com.msmisa.TicketApp.beans.HallSegment;
import com.msmisa.TicketApp.dto.ForeignKeyDTO;

public class HallCreationDTO {
	private String name;
	@ForeignKeyDTO(clazzFK=Auditorium.class)
	private Integer auditorium;
	@ForeignKeyDTO(clazzFK=HallSegment.class)
	private List<Integer> hallSegmentListIds;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAuditorium() {
		return auditorium;
	}
	public void setAuditorium(Integer auditorium) {
		this.auditorium = auditorium;
	}
	public List<Integer> getHallSegmentListIds() {
		return hallSegmentListIds;
	}
	public void setHallSegmentListIds(List<Integer> hallSegmentListIds) {
		this.hallSegmentListIds = hallSegmentListIds;
	}
	
	
}
