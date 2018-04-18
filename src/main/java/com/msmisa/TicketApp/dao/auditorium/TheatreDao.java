package com.msmisa.TicketApp.dao.auditorium;

import java.util.Set;

import com.msmisa.TicketApp.beans.Play;
import com.msmisa.TicketApp.beans.Theatre;
import com.msmisa.TicketApp.dao.DaoException;
import com.msmisa.TicketApp.dao.GenericDao;

public interface TheatreDao extends GenericDao<Theatre, Integer> {

	Set<Play> getAllPlays(Integer theatreId) throws DaoException;
	Theatre getOneWithPlays(Integer theatreId) throws DaoException;
}
