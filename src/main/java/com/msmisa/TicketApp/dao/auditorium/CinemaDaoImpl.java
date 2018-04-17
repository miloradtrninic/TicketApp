package com.msmisa.TicketApp.dao.auditorium;

import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.msmisa.TicketApp.beans.Cinema;
import com.msmisa.TicketApp.beans.Movie;
import com.msmisa.TicketApp.dao.AbstractGenericDao;
import com.msmisa.TicketApp.dao.DaoException;

@Repository
@Transactional
public class CinemaDaoImpl extends AbstractGenericDao<Cinema, Integer> implements CinemaDao {
	
	@Autowired
	public CinemaDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Set<Movie> getAllMovies(Integer cinemaId) throws DaoException {
		Cinema cinema = get(cinemaId);
		Hibernate.initialize(cinema.getMoviesRepertory());
		return cinema.getMoviesRepertory();
	}
	
	@Override
	public Cinema getOneWithMovies(Integer cinemaId) throws DaoException {
		Cinema cinema = get(cinemaId);
		Hibernate.initialize(cinema.getMoviesRepertory());
		return cinema;
	}
	
	
	

}
