package com.msmisa.TicketApp.dto.creation;

import com.msmisa.TicketApp.beans.Reservation;
import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dto.ForeignKeyDTO;

public class InvitationCreationDTO {
	@ForeignKeyDTO(clazzFK=User.class)
	private Integer invitedUser;
	@ForeignKeyDTO(clazzFK=Reservation.class)
	private Integer reservation;
	
	public Integer getInvitedUser() {
		return invitedUser;
	}
	public void setInvitedUser(Integer invitedUser) {
		this.invitedUser = invitedUser;
	}
	public Integer getReservation() {
		return reservation;
	}
	public void setReservation(Integer reservation) {
		this.reservation = reservation;
	}
	
	
}
