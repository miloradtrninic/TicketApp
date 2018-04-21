package com.msmisa.TicketApp.dto.preview;

public class MyBidPreviewDTO extends BidPreviewDTO {
	private String fanAdName;
	private Boolean accepted;
	public String getFanAdName() {
		return fanAdName;
	}

	public void setFanAdName(String fanAdName) {
		this.fanAdName = fanAdName;
	}

	public Boolean getAccepted() {
		return accepted;
	}

	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}
	
	
}
