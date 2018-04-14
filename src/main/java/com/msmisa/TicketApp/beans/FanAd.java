package com.msmisa.TicketApp.beans;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="FANADS")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class FanAd {
	private Integer id;
	private String name;
	private String description;
	private Date dateCreated;
	private String imagePath;
	private User postedBy;
	private FanZone fanZone;
	private FanItem fanItem;
	private Boolean accepted;
	private Date expirationDate;
	private List<Bid> bidList;
	private User admin;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
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
	@JsonBackReference(value="ads_users")
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	public User getPostedBy() {
		return postedBy;
	}
	public void setPostedBy(User postedBy) {
		this.postedBy = postedBy;
	}
	@OneToOne
	@JoinColumn(name="FANZONE_ID")
	public FanZone getFanZone() {
		return fanZone;
	}
	public void setFanZone(FanZone fanZone) {
		this.fanZone = fanZone;
	}
	@OneToOne
	@JoinColumn(name="FANITEM_ID")
	public FanItem getFanItem() {
		return fanItem;
	}
	public void setFanItem(FanItem fanItem) {
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
	@OneToMany(mappedBy="fanAd")
	@JsonManagedReference(value="bid_ad")
	@Cascade(value=CascadeType.ALL)
	public List<Bid> getBidList() {
		return bidList;
	}
	public void setBidList(List<Bid> bidList) {
		this.bidList = bidList;
	}
	
	@ManyToOne
	public User getAdmin() {
		return admin;
	}
	public void setAdmin(User admin) {
		this.admin = admin;
	}
	
	
	
}
