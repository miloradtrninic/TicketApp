package com.msmisa.TicketApp.dto.preview;

public class InvitationPreviewDTO {
	private Integer id;
	private String invitedUserUsername;
	private ReservationPreviewDTO reservation;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInvitedUserUsername() {
		return invitedUserUsername;
	}
	public void setInvitedUserUsername(String invitedUserUsername) {
		this.invitedUserUsername = invitedUserUsername;
	}
	public ReservationPreviewDTO getReservation() {
		return reservation;
	}
	public void setReservation(ReservationPreviewDTO reservation) {
		this.reservation = reservation;
	}
	
	
}
