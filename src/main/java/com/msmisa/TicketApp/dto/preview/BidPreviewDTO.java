package com.msmisa.TicketApp.dto.preview;

import java.util.Date;

public class BidPreviewDTO {
	private Integer id;
	private Double offer;
	private String fromUserUsername;
	private Date offerDate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getOffer() {
		return offer;
	}
	public void setOffer(Double offer) {
		this.offer = offer;
	}
	public String getFromUserUsername() {
		return fromUserUsername;
	}
	public void setFromUserUsername(String fromUserUsername) {
		this.fromUserUsername = fromUserUsername;
	}
	public Date getOfferDate() {
		return offerDate;
	}
	public void setOfferDate(Date offerDate) {
		this.offerDate = offerDate;
	}
	
}
