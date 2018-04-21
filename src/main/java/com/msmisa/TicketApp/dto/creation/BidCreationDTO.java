package com.msmisa.TicketApp.dto.creation;

import com.msmisa.TicketApp.beans.FanAd;
import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dto.ForeignKeyDTO;

public class BidCreationDTO {
	private Double offer;
	
	@ForeignKeyDTO(clazzFK=FanAd.class)
	private int adFK;
	
	public Double getOffer() {
		return offer;
	}
	public void setOffer(Double offer) {
		this.offer = offer;
	}
	public int getAdFK() {
		return adFK;
	}
	public void setAdFK(int adFK) {
		this.adFK = adFK;
	}
	
	
	
}