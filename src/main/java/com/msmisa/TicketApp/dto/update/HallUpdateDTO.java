package com.msmisa.TicketApp.dto.update;

import java.util.List;

import com.msmisa.TicketApp.dto.preview.HallSegmentPreviewDTO;

public class HallUpdateDTO {
	private Integer id;
	private String name;
	
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

	public List<HallSegmentPreviewDTO> getHallSegmentList() {
		return hallSegmentList;
	}

	public void setHallSegmentList(List<HallSegmentPreviewDTO> hallSegmentList) {
		this.hallSegmentList = hallSegmentList;
	}
	
	
}
