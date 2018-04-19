package com.msmisa.TicketApp.dto.update;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TerminUpdateDTO {
	
	private Integer id;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date date;
	private Integer price;
	private boolean discount;
	
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
