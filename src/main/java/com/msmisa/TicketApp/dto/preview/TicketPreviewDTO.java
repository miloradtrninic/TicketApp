package com.msmisa.TicketApp.dto.preview;

import java.util.Date;

public class TicketPreviewDTO {
	private ProjectionPreviewDTO projection;
	private Integer id;
	private Date time;
	private SeatingPreviewDTO seating;
	private Double price;
	private Integer discount;
	private Boolean quickReservation;
	
	public ProjectionPreviewDTO getProjection() {
		return projection;
	}
	public void setProjection(ProjectionPreviewDTO projection) {
		this.projection = projection;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public SeatingPreviewDTO getSeating() {
		return seating;
	}
	public void setSeating(SeatingPreviewDTO seating) {
		this.seating = seating;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public Boolean getQuickReservation() {
		return quickReservation;
	}
	public void setQuickReservation(Boolean quickReservation) {
		this.quickReservation = quickReservation;
	}
}
