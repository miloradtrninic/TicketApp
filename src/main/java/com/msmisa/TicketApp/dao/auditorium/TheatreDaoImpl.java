package com.msmisa.TicketApp.dao.auditorium;

import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.Cinema;
import com.msmisa.TicketApp.beans.Play;
import com.msmisa.TicketApp.beans.Theatre;
import com.msmisa.TicketApp.dao.AbstractGenericDao;
import com.msmisa.TicketApp.dao.DaoException;

@Repository
@Transactional
public class TheatreDaoImpl extends AbstractGenericDao<Theatre, Integer>  implements TheatreDao {

	@Autowired
	public TheatreDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public Set<Play> getAllPlays(Integer theatreId) throws DaoException {
		Theatre theatre = get(theatreId);
		Hibernate.initialize(theatre.getPlaysRepertory());
		return theatre.getPlaysRepertory();
	}


	
	@Override
	public Theatre getOneWithPlays(Integer theatreId) throws DaoException {
		Theatre theatre = get(theatreId);
		Hibernate.initialize(theatre.getPlaysRepertory());
		return theatre;
	}

}
