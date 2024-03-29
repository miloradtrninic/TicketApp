package com.msmisa.TicketApp.dao.hall;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.Hall;
import com.msmisa.TicketApp.dao.AbstractGenericDao;
import com.msmisa.TicketApp.dao.DaoException;

@Repository
@Transactional
public class HallDaoImpl extends AbstractGenericDao<Hall, Integer> implements HallDao {

	@Autowired
	public HallDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Hall> getByAuditoriumId(Integer id) throws DaoException {
		// TODO Auto-generated method stub
		try {
		return getSessionFactory()
						.getCurrentSession()
						.createCriteria(Hall.class)
						.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
						.createAlias("auditorium", "aud")
						.add(Restrictions.eq("aud.id", id))
						.list();
		} catch (Exception e) {
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public List<Hall> getAllOfMyAuds(String username) throws DaoException {
		try {
			return getSessionFactory().getCurrentSession().createCriteria(Hall.class)
					.createAlias("auditorium", "aud")
					.createAlias("aud.admin", "ad")
					.add(Restrictions.eq("ad.username", username))
					.list();
		} catch (Exception e) {
			throw new DaoException(e.getMessage());
		}
	}
	

}
