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
@Table(name="FANITEMS")
public class FanItem {
	private Integer id;
	private Projection projection;
	private FanZone fanzone;
	
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
	@ManyToOne
	@JoinColumn(name="FANZONE_ID", nullable=false)
	public FanZone getFanzone() {
		return fanzone;
	}

	public void setFanzone(FanZone fanzone) {
		this.fanzone = fanzone;
	}
	
	
	
	
}
