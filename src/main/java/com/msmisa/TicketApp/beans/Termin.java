package com.msmisa.TicketApp.beans;

import java.sql.Date;
import java.util.Set;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class Termin {
	private Projection projection;
	private Set<Hall> hallList;
	private Date time;
	private Integer price;
	

	@ManyToOne(optional=false)
	public Projection getProjection() {
		return projection;
	}
	public void setProjection(Projection projection) {
		this.projection = projection;
	}
	@ManyToMany
	@Cascade(value=CascadeType.ALL)
	@JoinTable(name="HALLS_TERMIN", joinColumns=@JoinColumn(name="PROJECTION_ID"), inverseJoinColumns=@JoinColumn(name="HALL_ID"))
	public Set<Hall> getHallList() {
		return hallList;
	}
	public void setHallList(Set<Hall> hallList) {
		this.hallList = hallList;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	
}
