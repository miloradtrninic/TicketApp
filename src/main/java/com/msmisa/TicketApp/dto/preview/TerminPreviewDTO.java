package com.msmisa.TicketApp.dto.preview;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;


public class TerminPreviewDTO {
	private Integer id;
	private List<String> hallListNames;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date date;
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
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	
}
