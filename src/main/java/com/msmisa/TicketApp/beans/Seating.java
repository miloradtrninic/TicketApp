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
@Table(name="SEATINGS")
public class Seating {
	private Integer id;
	private Integer row;
	private Integer number;
	private HallSegment hallSegment;
	private Boolean reserved;
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRow() {
		return row;
	}
	public void setRow(Integer row) {
		this.row = row;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	@ManyToOne
	@JsonBackReference(value="segment_seating")
	@JoinColumn(name="HALLSEGMENT_ID", nullable=false)
	public HallSegment getHallSegment() {
		return hallSegment;
	}
	public void setHallSegment(HallSegment hallSegment) {
		this.hallSegment = hallSegment;
	}
	public Boolean getReserved() {
		return reserved;
	}
	public void setReserved(Boolean reserved) {
		this.reserved = reserved;
	}
	
	
}
