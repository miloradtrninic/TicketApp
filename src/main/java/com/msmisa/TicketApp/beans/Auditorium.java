package com.msmisa.TicketApp.beans;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "AUDITORIUM")  
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "entityType",discriminatorType = DiscriminatorType.STRING)
public abstract class Auditorium {
	private Integer id;
	private String name;
	private String address;
	private String description;
	private Integer ratings;
	private FanZone fanZone;
	private String type;
	
	private Set<Hall> hallList;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getID() {
		return id;
	}
	public void setID(Integer iD) {
		id = iD;
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
	@JoinColumn(name="FANZONE_ID", nullable=true)
	public FanZone getFanZone() {
		return fanZone;
	}
	public void setFanZone(FanZone fanZone) {
		this.fanZone = fanZone;
	}
	
	@JsonIgnore
	@OneToMany(mappedBy="auditorium")
	@Cascade(value=CascadeType.ALL)
	public Set<Hall> getHallList() {
		return hallList;
	}
	public void setHallList(Set<Hall> hallList) {
		this.hallList = hallList;
	}
	@Column(name="entity_type", nullable=false)
	public String getType() {
		return type;
	}
	
	
	
	
}
