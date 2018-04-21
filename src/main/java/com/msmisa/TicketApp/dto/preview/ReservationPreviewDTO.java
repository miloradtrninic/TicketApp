package com.msmisa.TicketApp.dto.preview;

import java.util.List;

import com.msmisa.TicketApp.dto.ForeignKeyDTO;
import com.msmisa.TicketApp.dto.creation.TicketCreationDTO;
import com.msmisa.TicketApp.dto.creation.UserCreationDTO;

public class ReservationPreviewDTO {
	private Integer id;
	private UserPreviewDTO reservedBy;
	//@ForeignKeyDTO(clazzFK=TicketCreationDTO.class)
	private List<TicketPreviewDTO> ticketList;
	private Double price;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public UserPreviewDTO getReservedBy() {
		return reservedBy;
	}
	public void setReservedBy(UserPreviewDTO reservedBy) {
		this.reservedBy = reservedBy;
	}
	public List<TicketPreviewDTO> getTicketList() {
		return ticketList;
	}
	public void setTicketList(List<TicketPreviewDTO> ticketList) {
		this.ticketList = ticketList;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	
}
