package com.msmisa.TicketApp.beans;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
	private Set<User> admin;
	
	public Auditorium() {
	}
	
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
	@OneToOne(mappedBy="auditorium")
	public FanZone getFanZone() {
		return fanZone;
	}
	public void setFanZone(FanZone fanZone) {
		this.fanZone = fanZone;
	}
	@ManyToMany
	@JoinTable(name="AUDITORIUM_ADMIN", joinColumns=@JoinColumn(name="AUDITORIUM_ID", referencedColumnName="ID"),
										inverseJoinColumns=@JoinColumn(name="ADMIN_ID", referencedColumnName="ID"))
	public Set<User> getAdmin() {
		return admin;
	}
	public void setAdmin(Set<User> admin) {
		this.admin = admin;
	}
	
	
	
	
	
}
