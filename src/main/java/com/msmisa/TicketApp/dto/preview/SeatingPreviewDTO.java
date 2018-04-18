package com.msmisa.TicketApp.dto.preview;

public class SeatingPreviewDTO {
	private Integer id;
	private Integer row;
	private Integer number;
	private Boolean reserved;
	private HallSegmentPreviewDTO hallSegment;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	public Boolean getReserved() {
		return reserved;
	}
	public void setReserved(Boolean reserved) {
		this.reserved = reserved;
	}
	public HallSegmentPreviewDTO getHallSegment() {
		return hallSegment;
	}
	public void setHallSegment(HallSegmentPreviewDTO hallSegment) {
		this.hallSegment = hallSegment;
	}
	
	
}
