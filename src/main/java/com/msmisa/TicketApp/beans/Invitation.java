package com.msmisa.TicketApp.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="INVITATIONS", catalog="isadb")
public class Invitation {
	
	private Integer id;
	private User invitedUser;
	private Reservation reservation;
	
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
	@JoinColumn(name="INVITED_USER_ID", nullable=false)
	public User getInvitedUser() {
		return invitedUser;
	}
	public void setInvitedUser(User invitedUser) {
		this.invitedUser = invitedUser;
	}
	@OneToOne
	@JoinColumn(name="RESERVATION_ID", nullable=false)
	public Reservation getReservation() {
		return reservation;
	}
	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
	
	
}
