package com.msmisa.TicketApp.dao.ticket;

import java.util.List;

import com.msmisa.TicketApp.beans.Ticket;
import com.msmisa.TicketApp.dao.GenericDao;

public interface TicketDao extends GenericDao<Ticket, Integer>{
	List<Ticket> getTicketsForProjection(Integer id);
}
