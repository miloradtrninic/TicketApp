package com.msmisa.TicketApp.dao.hall;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.Seating;
import com.msmisa.TicketApp.dao.AbstractGenericDao;

@Repository
@Transactional
public class SeatingDaoImpl extends AbstractGenericDao<Seating, Integer> implements SeatingDao {
	
	@Autowired
	public SeatingDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}

}
