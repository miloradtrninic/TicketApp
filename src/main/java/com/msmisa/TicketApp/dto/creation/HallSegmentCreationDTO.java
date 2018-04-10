package com.msmisa.TicketApp.dto.creation;

import java.util.List;

import com.msmisa.TicketApp.beans.Hall;
import com.msmisa.TicketApp.beans.Seating;
import com.msmisa.TicketApp.dto.ForeignKeyDTO;

public class HallSegmentCreationDTO {
	@ForeignKeyDTO(clazzFK=Hall.class)
	private Integer hallId;
	private String name;
	private Integer seatingsNo;
	@ForeignKeyDTO(clazzFK=Seating.class)
	private List<Integer> seatingList;
	
	
	public Integer getHallId() {
		return hallId;
	}
	public void setHallId(Integer hallId) {
		this.hallId = hallId;
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
	public List<Integer> getSeatingList() {
		return seatingList;
	}
	public void setSeatingList(List<Integer> seatingList) {
		this.seatingList = seatingList;
	}
	
	
}
