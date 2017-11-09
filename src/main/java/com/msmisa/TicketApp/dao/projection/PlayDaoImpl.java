package com.msmisa.TicketApp.dao.projection;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.Play;
import com.msmisa.TicketApp.dao.AbstractGenericDao;

@Repository
@Transactional
public class PlayDaoImpl extends AbstractGenericDao<Play, Integer> implements PlayDao {

	@Autowired
	public PlayDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}

}
