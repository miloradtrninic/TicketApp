package com.msmisa.TicketApp.beans;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name="BIDS")
public class Bid {
	private Integer id;
	//private User toUser; iz fanAd se vidi kome je
	private User fromUser;
	private Double offer;
	private FanAd fanAd;
	private Date offerDate;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@JsonBackReference(value="bids_user")
	@ManyToOne
	@JoinColumn(name="FROM_USER", nullable=false)
	public User getFromUser() {
		return fromUser;
	}
	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}
	public Double getOffer() {
		return offer;
	}
	public void setOffer(Double offer) {
		this.offer = offer;
	}
	@ManyToOne
	@JoinColumn(name="FANAD_ID", nullable=false)
	public FanAd getFanAd() {
		return fanAd;
	}
	public void setFanAd(FanAd fanAd) {
		this.fanAd = fanAd;
	}
	public Date getOfferDate() {
		return offerDate;
	}
	public void setOfferDate(Date offerDate) {
		this.offerDate = offerDate;
	}
	
	
}
