package com.msmisa.TicketApp.dto.creation;

import com.msmisa.TicketApp.beans.Theatre;
import com.msmisa.TicketApp.dto.ForeignKeyDTO;

public class PlayCreationDTO extends ProjectionCreationDTO {
	@ForeignKeyDTO(clazzFK=Theatre.class)
	private Integer theaFK;

	public Integer getTheaFK() {
		return theaFK;
	}

	public void setTheaFK(Integer theaFK) {
		this.theaFK = theaFK;
	}
	
	
}
