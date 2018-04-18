package com.msmisa.TicketApp.dto.creation;

import com.msmisa.TicketApp.beans.Cinema;
import com.msmisa.TicketApp.dto.ForeignKeyDTO;

public class MovieCreationDTO extends ProjectionCreationDTO{
	@ForeignKeyDTO(clazzFK = Cinema.class)
	private Integer cinFK;

	public Integer getCinFK() {
		return cinFK;
	}

	public void setCinFK(Integer cinFK) {
		this.cinFK = cinFK;
	}
	
	
}
