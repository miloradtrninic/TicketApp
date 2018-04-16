package com.msmisa.TicketApp.dao.projection;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.Termin;
import com.msmisa.TicketApp.dao.AbstractGenericDao;

@Repository
@Transactional
public class TerminDaoImpl extends AbstractGenericDao<Termin, Integer> implements TerminDao {

	@Autowired
	public TerminDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}

}
