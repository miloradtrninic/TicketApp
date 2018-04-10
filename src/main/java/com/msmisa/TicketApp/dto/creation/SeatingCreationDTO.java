package com.msmisa.TicketApp.dto.creation;

import com.msmisa.TicketApp.beans.HallSegment;
import com.msmisa.TicketApp.dto.ForeignKeyDTO;

public class SeatingCreationDTO {
	private Integer row;
	private Integer number;
	@ForeignKeyDTO(clazzFK=HallSegment.class)
	private Integer hallSegmentId;
	private Boolean reserved;
	
	public Integer getRow() {
		return row;
	}
	public void setRow(Integer row) {
		this.row = row;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getHallSegmentId() {
		return hallSegmentId;
	}
	public void setHallSegmentId(Integer hallSegmentId) {
		this.hallSegmentId = hallSegmentId;
	}
	public Boolean getReserved() {
		return reserved;
	}
	public void setReserved(Boolean reserved) {
		this.reserved = reserved;
	}
	
	
	
}
