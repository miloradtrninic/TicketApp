package com.msmisa.TicketApp.dto.creation;

import java.util.Date;

import com.msmisa.TicketApp.beans.FanItem;
import com.msmisa.TicketApp.beans.FanZone;
import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dto.ForeignKeyDTO;

public class FanAdCreationDTO {
	private String name;
	private String description;
	private Date dateCreated;
	private String imagePath;
	
	@ForeignKeyDTO(clazzFK=User.class)
	private int postedByUserId;
	
	@ForeignKeyDTO(clazzFK=FanZone.class)
	private int fanZoneId;
	
	@ForeignKeyDTO(clazzFK=FanItem.class)
	private int fanItemId;
	
	private Boolean accepted;
	private Date expirationDate;
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
	public int getPostedByUserName() {
		return postedByUserId;
	}
	public void setPostedByUserName(int postedByUserName) {
		this.postedByUserId = postedByUserName;
	}
	public Integer getFanZoneId() {
		return fanZoneId;
	}
	public void setFanZoneId(Integer fanZoneId) {
		this.fanZoneId = fanZoneId;
	}
	public Integer getFanItemId() {
		return fanItemId;
	}
	public void setFanItemId(Integer fanItemId) {
		this.fanItemId = fanItemId;
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
	
	
	
	
}
