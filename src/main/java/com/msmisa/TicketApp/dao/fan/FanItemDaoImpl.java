package com.msmisa.TicketApp.dao.fan;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.FanItem;
import com.msmisa.TicketApp.dao.AbstractGenericDao;
import com.msmisa.TicketApp.dao.DaoException;

@Transactional
@Repository
public class FanItemDaoImpl extends AbstractGenericDao<FanItem, Integer> implements FanItemDao {

	@Autowired
	public FanItemDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public List<FanItem> getByZone(Integer zoneID) throws DaoException {
		// TODO Auto-generated method stub
		try {
			return getSessionFactory()
					.getCurrentSession()
					.createCriteria(FanItem.class)
					.createAlias("fanZone", "fz")
					.add(Restrictions.eq("fz.id", zoneID))
					.list();
		} catch (HibernateException e) {
			throw new DaoException(e.getMessage());
		}
	}

}
