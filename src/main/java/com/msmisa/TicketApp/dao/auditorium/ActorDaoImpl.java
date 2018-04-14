package com.msmisa.TicketApp.dao.auditorium;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.msmisa.TicketApp.beans.movie.Actor;
import com.msmisa.TicketApp.dao.AbstractGenericDao;

@Repository
@Transactional
public class ActorDaoImpl extends AbstractGenericDao<Actor, Integer>  implements ActorDao {

	@Autowired
	public ActorDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}