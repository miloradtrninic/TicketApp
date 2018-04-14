package com.msmisa.TicketApp.dao.auditorium;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.msmisa.TicketApp.beans.movie.Genre;
import com.msmisa.TicketApp.dao.AbstractGenericDao;

@Repository
@Transactional
public class GenreDaoImpl extends AbstractGenericDao<Genre, Integer>  implements GenreDao {

	@Autowired
	public GenreDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}