package com.msmisa.TicketApp.dao.ticket;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.Reservation;
import com.msmisa.TicketApp.dao.AbstractGenericDao;

@Repository
@Transactional
public class ReservationDaoImpl extends AbstractGenericDao<Reservation, Integer> {

	@Autowired
	public ReservationDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}
