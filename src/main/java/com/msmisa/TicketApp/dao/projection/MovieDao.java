package com.msmisa.TicketApp.dao.projection;

import java.util.List;

import com.msmisa.TicketApp.beans.Movie;
import com.msmisa.TicketApp.dao.DaoException;
import com.msmisa.TicketApp.dao.GenericDao;

public interface MovieDao extends GenericDao<Movie, Integer> {
	List<Movie> getAllFromCinema(Integer cinemaId) throws DaoException;
}
