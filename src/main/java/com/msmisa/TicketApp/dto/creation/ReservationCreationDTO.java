package com.msmisa.TicketApp.dto.creation;

import java.util.List;

import com.msmisa.TicketApp.dto.ForeignKeyDTO;
import com.msmisa.TicketApp.dto.preview.UserPreviewDTO;

public class ReservationCreationDTO {
	
	private Integer id;
	@ForeignKeyDTO(clazzFK=UserCreationDTO.class)
	private Integer reservedBy;
	private List<Integer> ticketList;
	private List<Integer> invitedUsersID;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getReservedBy() {
		return reservedBy;
	}

	public void setReservedBy(Integer reservedBy) {
		this.reservedBy = reservedBy;
	}

	public List<Integer> getTicketList() {
		return ticketList;
	}

	public void setTicketList(List<Integer> ticketList) {
		this.ticketList = ticketList;
	}
	public List<Integer> getInvitedUsersID() {
		return invitedUsersID;
	}

	public void setInvitedUsersID(List<Integer> invitedUsersID) {
		this.invitedUsersID = invitedUsersID;
	}
}
