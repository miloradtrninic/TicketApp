package com.msmisa.TicketApp.dao.projection;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.Projection;
import com.msmisa.TicketApp.beans.WatchedProjection;
import com.msmisa.TicketApp.dao.AbstractGenericDao;
import com.msmisa.TicketApp.dao.DaoException;

@Repository
@Transactional
public class ProjectionDaoImpl extends AbstractGenericDao<Projection, Integer> implements ProjectionDao  {

	public ProjectionDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	@Override
	public List<Projection> getWatchedBy(String username){
		try {
			return getSessionFactory()
					.getCurrentSession()
					.createQuery("select wp.pk.projection from WatchedProjection wp "
							+ " JOIN wp.pk.user u "
							+ " JOIN wp.pk.projection p "
							+ " WHERE u.username = :usern ")
					.setParameter("usern", username)
					.list();
		} catch(Exception e) {
			throw new DaoException(e.getMessage());
		}
	}
	@Override
	public void rateProjection(String username, Integer projectionId, Double rating) throws DaoException {
		try {
			List<WatchedProjection> projeWatched = getSessionFactory()
					.getCurrentSession()
					.createCriteria(WatchedProjection.class)
					.createAlias("pk.user", "user")
					.createAlias("pk.projection", "projection")
					.add(Restrictions.eq("user.username", username))
					.add(Restrictions.eq("projection.id", projectionId))
					.setMaxResults(1)
					.list();
			WatchedProjection one = projeWatched.get(0);
			one.setRating(rating);
			getSessionFactory().getCurrentSession().saveOrUpdate(one);
		}
		catch(Exception e) {
			throw new DaoException(e.getMessage());
		}
	}
}
