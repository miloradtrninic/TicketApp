package com.msmisa.TicketApp.dto.preview;

import java.util.Date;
import java.util.List;

public class TerminPreviewDTO {
	private Integer id;
	private List<String> hallListNames;
	private Date time;
	private Integer price;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<String> getHallListNames() {
		return hallListNames;
	}
	public void setHallListNames(List<String> hallListNames) {
		this.hallListNames = hallListNames;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	
}
