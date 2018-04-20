package com.msmisa.TicketApp.dto.creation;

import com.msmisa.TicketApp.beans.FanAd;
import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dto.ForeignKeyDTO;

public class BidCreationDTO {
	private String offer;
	
	@ForeignKeyDTO(clazzFK=FanAd.class)
	private int fanAdId;
	
	public String getOffer() {
		return offer;
	}
	public void setOffer(String offer) {
		this.offer = offer;
	}
	public Integer getFanAdId() {
		return fanAdId;
	}
	public void setFanAdId(Integer fanAdId) {
		this.fanAdId = fanAdId;
	}
	
	
}