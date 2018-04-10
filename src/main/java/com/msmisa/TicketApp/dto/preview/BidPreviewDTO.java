package com.msmisa.TicketApp.dto.preview;

public class BidPreviewDTO {
	private Integer id;
	private UserPreviewDTO fromUser;
	private String offer;
	private FanAdPreviewDTO fanAd;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public UserPreviewDTO getFromUser() {
		return fromUser;
	}
	public void setFromUser(UserPreviewDTO fromUser) {
		this.fromUser = fromUser;
	}
	public String getOffer() {
		return offer;
	}
	public void setOffer(String offer) {
		this.offer = offer;
	}
	public FanAdPreviewDTO getFanAd() {
		return fanAd;
	}
	public void setFanAd(FanAdPreviewDTO fanAd) {
		this.fanAd = fanAd;
	}
	
	
}
