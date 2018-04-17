package com.msmisa.TicketApp.dao.auditorium;

import java.util.Set;

import com.msmisa.TicketApp.beans.Cinema;
import com.msmisa.TicketApp.beans.Movie;
import com.msmisa.TicketApp.dao.DaoException;
import com.msmisa.TicketApp.dao.GenericDao;

public interface CinemaDao extends GenericDao<Cinema, Integer> {
	Set<Movie> getAllMovies(Integer cinemaId) throws DaoException;
	Cinema getOneWithMovies(Integer cinemaId) throws DaoException;
}

