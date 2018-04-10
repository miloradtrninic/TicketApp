package com.msmisa.TicketApp.dto.creation;

import com.msmisa.TicketApp.beans.FanAd;
import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dto.ForeignKeyDTO;

public class BidCreationDTO {
	private String offer;
	
	@ForeignKeyDTO(clazzFK=User.class)
	private int fromUserId;
	
	@ForeignKeyDTO(clazzFK=FanAd.class)
	private int fanAdId;
	
	public String getOffer() {
		return offer;
	}
	public void setOffer(String offer) {
		this.offer = offer;
	}
	public int getFromUsername() {
		return fromUserId;
	}
	public void setFromUsername(int fromUserId) {
		this.fromUserId = fromUserId;
	}
	public Integer getFanAdId() {
		return fanAdId;
	}
	public void setFanAdId(Integer fanAdId) {
		this.fanAdId = fanAdId;
	}
	
	
}
