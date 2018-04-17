package com.msmisa.TicketApp.dto.creation;

import java.util.List;

import com.msmisa.TicketApp.dto.ForeignKeyDTO;

public class ReservationCreationDTO {
	
	private Integer id;
	@ForeignKeyDTO(clazzFK=UserCreationDTO.class)
	private UserCreationDTO reservedBy;
	private List<TicketCreationDTO> ticketList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getReservedBy() {
		return reservedBy;
	}

	public void setReservedBy(UserCreationDTO reservedBy) {
		this.reservedBy = reservedBy;
	}

	public List<TicketCreationDTO> getTicketList() {
		return ticketList;
	}

	public void setTicketList(List<TicketCreationDTO> ticketList) {
		this.ticketList = ticketList;
	} 
	
	
}
