package com.msmisa.TicketApp.dao.auditorium;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.Auditorium;
import com.msmisa.TicketApp.dao.AbstractGenericDao;

@Repository
@Transactional
public class AuditoriumDaoImpl extends AbstractGenericDao<Auditorium, Integer> implements AuditoriumDao {

	@Autowired
	public AuditoriumDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}
