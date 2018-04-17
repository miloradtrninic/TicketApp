package com.msmisa.TicketApp.dao.auditorium;

import java.util.List;

import com.msmisa.TicketApp.beans.Auditorium;
import com.msmisa.TicketApp.dao.DaoException;
import com.msmisa.TicketApp.dao.GenericDao;

public interface AuditoriumDao extends GenericDao<Auditorium, Integer>  {
	List<Auditorium> getByAdmin(String username) throws DaoException;
}
