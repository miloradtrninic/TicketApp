package com.msmisa.TicketApp.beans;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name="BIDS", catalog="isadb")
public class Bid {
	private Integer id;
	//private User toUser; iz fanAd se vidi kome je
	private User fromUser;
	private String offer;
	private FanAd fanAd;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="FROM_USER", nullable=false)
	public User getFromUser() {
		return fromUser;
	}
	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}
	public String getOffer() {
		return offer;
	}
	public void setOffer(String offer) {
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
	
}
