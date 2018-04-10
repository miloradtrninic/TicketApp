package com.msmisa.TicketApp.dto.preview;

import java.util.Date;
import java.util.List;

public class FanAdPreviewDTO {
	private Integer id;
	private String name;
	private String description;
	private Date dateCreated;
	private String imagePath;
	private UserPreviewDTO postedBy;
	private FanItemPreviewDTO fanItem;
	private Boolean accepted;
	private Date expirationDate;
	private List<BidPreviewDTO> bidList;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public UserPreviewDTO getPostedBy() {
		return postedBy;
	}
	public void setPostedBy(UserPreviewDTO postedBy) {
		this.postedBy = postedBy;
	}
	public FanItemPreviewDTO getFanItem() {
		return fanItem;
	}
	public void setFanItem(FanItemPreviewDTO fanItem) {
		this.fanItem = fanItem;
	}
	public Boolean getAccepted() {
		return accepted;
	}
	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public List<BidPreviewDTO> getBidList() {
		return bidList;
	}
	public void setBidList(List<BidPreviewDTO> bidList) {
		this.bidList = bidList;
	}
	
	
}
