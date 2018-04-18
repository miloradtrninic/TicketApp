package com.msmisa.TicketApp.dao.projection;

import java.util.List;

import com.msmisa.TicketApp.beans.Movie;
import com.msmisa.TicketApp.beans.Play;
import com.msmisa.TicketApp.dao.DaoException;
import com.msmisa.TicketApp.dao.GenericDao;

public interface PlayDao extends GenericDao<Play, Integer> {
	List<Play> getAllFromTheatre(Integer theatreId) throws DaoException;
}
