package com.msmisa.TicketApp.dto.creation;

import java.util.Date;

import com.msmisa.TicketApp.beans.Projection;
import com.msmisa.TicketApp.beans.Seating;
import com.msmisa.TicketApp.dto.ForeignKeyDTO;

public class TicketCreationDTO {
	@ForeignKeyDTO(clazzFK=Projection.class)
	private Integer projectionId;
	private Date time;
	@ForeignKeyDTO(clazzFK=Seating.class)
	private Integer seatingId;
	private Double price;
	private Integer discount;
	private Boolean quickReservation;
	
	public Integer getProjectionId() {
		return projectionId;
	}
	public void setProjectionId(Integer projectionId) {
		this.projectionId = projectionId;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Integer getSeatingId() {
		return seatingId;
	}
	public void setSeatingId(Integer seatingId) {
		this.seatingId = seatingId;
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
