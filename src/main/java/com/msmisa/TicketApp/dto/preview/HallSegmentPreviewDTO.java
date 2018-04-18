package com.msmisa.TicketApp.dto.preview;

import java.util.List;

import com.msmisa.TicketApp.dto.ForeignKeyDTO;

public class HallSegmentPreviewDTO {
	private Integer id;
	private String name;
	private Integer seatingsNo;
	private List<SeatingPreviewDTO> seatingList;
	@ForeignKeyDTO(clazzFK=HallPreviewDTO.class)
	private Integer hallId;
	
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
	public Integer getSeatingsNo() {
		return seatingsNo;
	}
	public void setSeatingsNo(Integer seatingsNo) {
		this.seatingsNo = seatingsNo;
	}
	public List<SeatingPreviewDTO> getSeatingList() {
		return seatingList;
	}
	public void setSeatingList(List<SeatingPreviewDTO> seatingList) {
		this.seatingList = seatingList;
	}
	public Integer getHallId() {
		return hallId;
	}
	public void setHallId(Integer hallId) {
		this.hallId = hallId;
	}
}
