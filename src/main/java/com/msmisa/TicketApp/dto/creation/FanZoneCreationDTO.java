package com.msmisa.TicketApp.dto.creation;

import java.util.List;

import com.msmisa.TicketApp.beans.Auditorium;
import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dto.ForeignKeyDTO;

public class FanZoneCreationDTO {
	@ForeignKeyDTO(clazzFK=User.class)
	private List<Integer> adminFKs;
	
	@ForeignKeyDTO(clazzFK=Auditorium.class)
	private Integer audFK;

	public List<Integer> getAdminFKs() {
		return adminFKs;
	}
	public void setAdminFKs(List<Integer> adminFKs) {
		this.adminFKs = adminFKs;
	}
	public Integer getAudFK() {
		return audFK;
	}
	public void setAudFK(Integer audFK) {
		this.audFK = audFK;
	}
	
	
	
	
}
