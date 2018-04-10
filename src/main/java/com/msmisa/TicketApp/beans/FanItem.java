package com.msmisa.TicketApp.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="FANITEMS")
public class FanItem {
	private Integer id;
	private String name;
	private String imagePath;
	private String description;
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
	@JsonBackReference(value="zone_item")
	@ManyToOne
	@JoinColumn(name="FANZONE_ID", nullable=true)
	public FanZone getFanzone() {
		return fanzone;
	}

	public void setFanzone(FanZone fanzone) {
		this.fanzone = fanzone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
}
