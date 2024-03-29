package com.msmisa.TicketApp.beans;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="FANZONES")
public class FanZone {
	private Integer id;
	private Set<User> admin;
	private Auditorium auditorium;
	private Set<FanItem> fanitemList;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToMany
	@JoinTable(name="FANZONE_ADMINS",
			joinColumns=@JoinColumn(name="FANZONE_ID", referencedColumnName="ID"),
			inverseJoinColumns=@JoinColumn(name="USER_ID", referencedColumnName="ID"))
	public Set<User> getAdmin() {
		return admin;
	}
	public void setAdmin(Set<User> admin) {
		this.admin = admin;
	}
	
	@OneToMany(mappedBy="fanzone", fetch=FetchType.EAGER, orphanRemoval=true)
	@JsonManagedReference(value="zone_item")
	@Cascade(value=CascadeType.ALL)
	public Set<FanItem> getFanitemList() {
		return fanitemList;
	}
	public void setFanitemList(Set<FanItem> fanitemList) {
		this.fanitemList = fanitemList;
	}
	
	@OneToOne(optional=false)
	@JoinColumn(name="AUDITORIUM_ID", nullable=false, unique=true)
	public Auditorium getAuditorium() {
		return auditorium;
	}
	public void setAuditorium(Auditorium auditorium) {
		this.auditorium = auditorium;
	}
	
	
}
