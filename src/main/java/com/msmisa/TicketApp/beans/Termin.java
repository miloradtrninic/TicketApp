package com.msmisa.TicketApp.beans;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="TERMIN")
public class Termin {
	
	private Integer id;
	
	private Projection projection;
	private Set<Hall> hallList;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	private Integer price;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TERMIN_ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	public Projection getProjection() {
		return projection;
	}
	public void setProjection(Projection projection) {
		this.projection = projection;
	}
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="TERMIN_HALLS",
			joinColumns=@JoinColumn(name="TERMIN_ID", referencedColumnName="TERMIN_ID"),
			inverseJoinColumns=@JoinColumn(name="HALL_ID", referencedColumnName="ID"))
	public Set<Hall> getHallList() {
		return hallList;
	}
	public void setHallList(Set<Hall> hallList) {
		this.hallList = hallList;
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	
}
