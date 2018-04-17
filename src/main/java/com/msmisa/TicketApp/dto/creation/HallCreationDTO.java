package com.msmisa.TicketApp.dto.creation;

import java.util.List;

import com.msmisa.TicketApp.beans.Auditorium;
import com.msmisa.TicketApp.beans.HallSegment;
import com.msmisa.TicketApp.dto.ForeignKeyDTO;
import com.msmisa.TicketApp.dto.preview.HallSegmentPreviewDTO;

public class HallCreationDTO {
	
	private String name;
	private Integer audId;
	
	private List<HallSegmentPreviewDTO> hallSegmentList;
	
	public HallCreationDTO() {
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAudId() {
		return audId;
	}
	public void setAudId(Integer audId) {
		this.audId = audId;
	}
	public List<HallSegmentPreviewDTO> getHallSegmentList() {
		return hallSegmentList;
	}
	public void setHallSegmentList(List<HallSegmentPreviewDTO> hallSegmentList) {
		this.hallSegmentList = hallSegmentList;
	}
	
	
}
