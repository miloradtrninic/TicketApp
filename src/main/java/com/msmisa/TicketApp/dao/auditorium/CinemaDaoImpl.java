package com.msmisa.TicketApp.dao.auditorium;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.msmisa.TicketApp.beans.Cinema;
import com.msmisa.TicketApp.dao.AbstractGenericDao;

@Repository
@Transactional
public class CinemaDaoImpl extends AbstractGenericDao<Cinema, Integer> implements CinemaDao {
	
	@Autowired
	public CinemaDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	} 
	
	
	

}
