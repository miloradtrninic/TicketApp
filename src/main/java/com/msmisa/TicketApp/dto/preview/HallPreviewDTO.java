package com.msmisa.TicketApp.dto.preview;

import java.util.List;

public class HallPreviewDTO {
	private Integer id;
	private String name;
	private String auditoriumName;
	private List<HallSegmentPreviewDTO> hallSegmentList;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuditoriumName() {
		return auditoriumName;
	}
	public void setAuditoriumName(String auditoriumName) {
		this.auditoriumName = auditoriumName;
	}
	public List<HallSegmentPreviewDTO> getHallSegmentList() {
		return hallSegmentList;
	}
	public void setHallSegmentList(List<HallSegmentPreviewDTO> hallSegmentList) {
		this.hallSegmentList = hallSegmentList;
	}
	
	
}
