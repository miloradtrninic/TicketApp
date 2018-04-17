package com.msmisa.TicketApp.beans;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="HALLS")
public class Hall {
	private Integer id;
	private String name;
	private Auditorium auditorium;
	private Set<HallSegment> hallSegmentList;
	
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
	@ManyToOne
	public Auditorium getAuditorium() {
		return auditorium;
	}
	public void setAuditorium(Auditorium auditorium) {
		this.auditorium = auditorium;
	}
	@OneToMany(orphanRemoval=true, fetch=FetchType.EAGER)
	@Cascade(value=CascadeType.ALL)
	@JoinColumn(name = "hall_id")
	public Set<HallSegment> getHallSegmentList() {
		return hallSegmentList;
	}
	public void setHallSegmentList(Set<HallSegment> hallSegmentList) {
		this.hallSegmentList = hallSegmentList;
	}
	
	
	
}
