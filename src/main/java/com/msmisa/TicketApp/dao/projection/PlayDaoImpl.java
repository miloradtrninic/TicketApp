package com.msmisa.TicketApp.dao.projection;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.Movie;
import com.msmisa.TicketApp.beans.Play;
import com.msmisa.TicketApp.dao.AbstractGenericDao;
import com.msmisa.TicketApp.dao.DaoException;

@Repository
@Transactional
public class PlayDaoImpl extends AbstractGenericDao<Play, Integer> implements PlayDao {

	@Autowired
	public PlayDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Play> getAllFromTheatre(Integer theatreId) throws DaoException {
		try{
			return getSessionFactory()
					.getCurrentSession()
					.createCriteria(Movie.class)
					.createAlias("theatreList", "the")
					.add(Restrictions.eq("id", theatreId))
					.list();
			} catch (Exception e) {
				throw new DaoException(e.getMessage());
			}
	}

}
