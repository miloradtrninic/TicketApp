package com.msmisa.TicketApp.dao.auditorium;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.movie.Director;
import com.msmisa.TicketApp.dao.AbstractGenericDao;

@Repository
@Transactional
public class DirectorDaoImpl extends AbstractGenericDao<Director, Integer>  implements DirectorDao {

	@Autowired
	public DirectorDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}

