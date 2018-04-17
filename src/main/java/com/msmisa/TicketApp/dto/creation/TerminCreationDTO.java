package com.msmisa.TicketApp.dto.creation;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.msmisa.TicketApp.beans.Hall;
import com.msmisa.TicketApp.beans.Projection;
import com.msmisa.TicketApp.dto.ForeignKeyDTO;

public class TerminCreationDTO {

	@ForeignKeyDTO(clazzFK=Projection.class)
	private Integer projFK;
	
	@ForeignKeyDTO(clazzFK=Hall.class)
	private List<Integer> hallFKs;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date date;
	private Integer price;
	private boolean discount;
	
	
	public Integer getProjFK() {
		return projFK;
	}
	public void setProjFK(Integer projFK) {
		this.projFK = projFK;
	}
	public List<Integer> getHallFKs() {
		return hallFKs;
	}
	public void setHallFKs(List<Integer> hallFKs) {
		this.hallFKs = hallFKs;
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
	public boolean isDiscount() {
		return discount;
	}
	public void setDiscount(boolean discount) {
		this.discount = discount;
	}
	
}
