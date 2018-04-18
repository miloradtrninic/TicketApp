package com.msmisa.TicketApp.beans;

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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="HALLSEGMENTS")
public class HallSegment {
	private Integer id;
	private Hall hall;
	private String name;
	private Integer seatingsNo;
	private Set<Seating> seatingList;
	
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
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name="HALL_ID", nullable=false)
	public Hall getHall() {
		return hall;
	}
	public void setHall(Hall hall) {
		this.hall = hall;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSeatingsNo() {
		return seatingsNo;
	}
	public void setSeatingsNo(Integer seatingsNo) {
		this.seatingsNo = seatingsNo;
	}
	
	@OneToMany(mappedBy="hallSegment", fetch=FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	public Set<Seating> getSeatingList() {
		return seatingList;
	}
	public void setSeatingList(Set<Seating> seatingList) {
		this.seatingList = seatingList;
	}
	
	
	
}
