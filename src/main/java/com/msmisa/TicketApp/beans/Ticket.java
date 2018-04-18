package com.msmisa.TicketApp.beans;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OptimisticLocking;
import org.springframework.data.annotation.Version;

import static javax.persistence.GenerationType.IDENTITY;



@Entity
@Table(name="TICKETS")
@OptimisticLocking
public class Ticket {
	private Projection projection;
	private Integer id;
	private Date time;
	private Seating seating;
	private Double price;
	private Integer discount;
	private Boolean quickReservation;
	@Version
	@NotNull
	private Integer version;
	
	
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
	@JoinColumn(name="PROJECTION_ID", nullable=false)
	public Projection getProjection() {
		return projection;
	}
	public void setProjection(Projection projection) {
		this.projection = projection;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	@OneToOne
	@JoinColumn(name="PROJECTION", nullable=false)
	public Seating getSeating() {
		return seating;
	}
	public void setSeating(Seating seating) {
		this.seating = seating;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public Boolean getQuickReservation() {
		return quickReservation;
	}
	public void setQuickReservation(Boolean quickReservation) {
		this.quickReservation = quickReservation;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	
}
