package com.msmisa.TicketApp.dao.projection;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.Projection;
import com.msmisa.TicketApp.dao.AbstractGenericDao;

@Repository
@Transactional
public class ProjectionDaoImpl extends AbstractGenericDao<Projection, Integer> implements ProjectionDao  {

	public ProjectionDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}
