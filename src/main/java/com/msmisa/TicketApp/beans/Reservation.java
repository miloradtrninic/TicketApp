package com.msmisa.TicketApp.beans;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="RESERVATIONS")
public class Reservation {
	private Integer id;
	private User reservedBy;
	private Set<Ticket> ticketList;
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@OneToOne
	@JoinColumn(name="RESERVED_BY")
	public User getReservedBy() {
		return reservedBy;
	}
	public void setReservedBy(User reservedBy) {
		this.reservedBy = reservedBy;
	}
	@OneToMany
	@JoinTable(name="TICKET_RESERERVATION",joinColumns=@JoinColumn(name="RESERVATION_ID"), inverseJoinColumns=@JoinColumn(name="TICKET_ID"))
	public Set<Ticket> getTicketList() {
		return ticketList;
	}
	public void setTicketList(Set<Ticket> ticketList) {
		this.ticketList = ticketList;
	}
	
	
}
