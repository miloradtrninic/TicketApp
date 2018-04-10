package com.msmisa.TicketApp.dao.auditorium;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.Theatre;
import com.msmisa.TicketApp.dao.AbstractGenericDao;

@Repository
@Transactional
public class TheatreDaoImpl extends AbstractGenericDao<Theatre, Integer>  implements TheatreDao {

	@Autowired
	public TheatreDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}

}
