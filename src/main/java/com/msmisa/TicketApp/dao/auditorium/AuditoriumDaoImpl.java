package com.msmisa.TicketApp.dao.auditorium;


import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.Auditorium;
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

}
