package com.msmisa.TicketApp.dao.auditorium;


import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.Auditorium;
import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.beans.UserAuditorium;
import com.msmisa.TicketApp.dao.AbstractGenericDao;
import com.msmisa.TicketApp.dao.DaoException;

@Repository
@Transactional
public class AuditoriumDaoImpl extends AbstractGenericDao<Auditorium, Integer> implements AuditoriumDao {

	@Autowired
	public AuditoriumDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public List<Auditorium> getByAdmin(String username) throws DaoException {
		try {
			return getSessionFactory()
				.getCurrentSession()
				.createCriteria(Auditorium.class)
				.createAlias("admin", "a")
				.add(Restrictions.eq("a.username", username))
				.list();
		} catch (Exception e) {
			throw new DaoException(e.getMessage());
		}
	}
	@Override
	public Set<User> getAdmins(Integer id) throws DaoException {
		// TODO Auto-generated method stub
		try {
			Auditorium aud = get(id);
			Hibernate.initialize(aud.getAdmin());
			return aud.getAdmin();
		} catch (Exception e) {
			throw new DaoException(e.getMessage());
		}
	}
	@Override
	public UserAuditorium rateAuditorium(Integer id, String username, Double rating) throws DaoException {
		try {
			Auditorium aud = getSessionFactory().getCurrentSession().get(Auditorium.class, id);
			if(aud == null) {
				throw new DaoException("Auditorium doesn't exist.");
			}
			User user = (User) getSessionFactory().getCurrentSession()
						.createCriteria(User.class)
						.add(Restrictions.eq("username", username))
						.list().get(0);
			if(user == null) {
				throw new DaoException("User doesn't exist.");
			}
			UserAuditorium userAud = null;
			/*long count = (long) getSessionFactory()
					.getCurrentSession()
					.createCriteria(UserAuditorium.class)
					.createAlias("pk.auditorium", "aud")
					.add(Restrictions.eq("aud.id", id))
					.setProjection(Projections.rowCount());*/
			logger.info("count starte");
			long count = (long) getSessionFactory()
								.getCurrentSession()
								.createQuery("select count(*) from UserAuditorium where AUDITORIUM_ID = :aud_id" )
								.setParameter("aud_id", id)
								.uniqueResult();
			double newrating = (count * aud.getRatings() + rating) / (count + 1);
			logger.info("counted " + count);
			/*List<UserAuditorium> ratings = getSessionFactory()
				.getCurrentSession()
				.createCriteria(UserAuditorium.class)
				.createAlias("pk", "pk")
				.createAlias("pk.user", "user")
				.createAlias("pk.auditorium", "aud")
				.add(Restrictions.eq("user.username", username))
				.add(Restrictions.eq("aud.id", id))
				.setMaxResults(1)
				.list();*/
			List<UserAuditorium> ratings = getSessionFactory()
				.getCurrentSession()
				.createQuery("select ua from UserAuditorium ua"
						+ " JOIN ua.pk.auditorium aud"
						+ " JOIN ua.pk.user u"
						+ " where aud.id = :aud_id and u.username = :usern")
				.setParameter("aud_id", id)
				.setParameter("usern", username)
				.list();
			
			logger.info("got ratings " + count);
			
			if(ratings == null || ratings.size() == 0) {
				logger.info("ratings is null or empty " + count);
				userAud = new UserAuditorium();
				userAud.setUser(user);
				userAud.setAuditorium(aud);
				userAud.setRating(rating);
				aud.setRatings(newrating);
				logger.info("prosli setteri");
				getSessionFactory().getCurrentSession().update(aud);
				getSessionFactory().getCurrentSession().save(userAud);
				logger.info("prosao save");
				userAud.setRating(newrating);
				return userAud;
			} else {
				userAud = ratings.get(0);
				userAud.setRating(aud.getRatings());
				return userAud;
			}
		} catch (Exception e) {
			throw new DaoException(e.getMessage());
		}
	}
}
