package com.msmisa.TicketApp.dao.ticket;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.Ticket;
import com.msmisa.TicketApp.dao.AbstractGenericDao;

@Repository
@Transactional
public class TicketDaoImpl extends AbstractGenericDao<Ticket, Integer> implements TicketDao {

	@Autowired
	public TicketDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}

}
