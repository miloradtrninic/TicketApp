package com.msmisa.TicketApp.beans;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn(name = "entityType",discriminatorType = DiscriminatorType.STRING)
public abstract class Auditorium {
	private Integer ID;
	private String name;
	private String address;
	private String description;
	private Integer ratings;
	private FanZone fanZone;
	private List<Hall> hallList;
	private List<Membership> membershipList;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getRatings() {
		return ratings;
	}
	public void setRatings(Integer ratings) {
		this.ratings = ratings;
	}
	@OneToOne
	@JoinColumn(name="FANZONE_ID", nullable=false)
	public FanZone getFanZone() {
		return fanZone;
	}
	public void setFanZone(FanZone fanZone) {
		this.fanZone = fanZone;
	}
	@OneToMany(mappedBy="auditorium")
	@Cascade(value=CascadeType.ALL)
	public List<Hall> getHallList() {
		return hallList;
	}
	public void setHallList(List<Hall> hallList) {
		this.hallList = hallList;
	}
	@ManyToMany
	@JoinTable(name="AUDITORIUM_MEMBERSHIP", joinColumns=@JoinColumn(name="AUDITORIUM_ID"), inverseJoinColumns=@JoinColumn(name="MEMBERSHIP_ID"))
	public List<Membership> getMembershipList() {
		return membershipList;
	}
	public void setMembershipList(List<Membership> membershipList) {
		this.membershipList = membershipList;
	}
	
	
	
	
}
