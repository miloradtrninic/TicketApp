package com.msmisa.TicketApp.dao.fan;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.FanItem;
import com.msmisa.TicketApp.beans.User;
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
					.createAlias("fanzone", "fz")
					.add(Restrictions.eq("fz.id", zoneID))
					.add(Restrictions.isNull("this.reservedBy"))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.list();
		} catch (HibernateException e) {
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public FanItem reserve(String username, Integer id) throws DaoException {
		try {
			List<User> users = getSessionFactory()
						.getCurrentSession()
						.createCriteria(User.class)
						.add(Restrictions.eq("username", username))
						.setMaxResults(1)
						.list();
			
			User user = users.get(0);
			logger.info("username reserve " + user.getUsername());
			FanItem item = get(id);
			logger.info("item id" + item.getId());
			if(item.getReservedBy() != null) {
				logger.info("won't reserve");
				return item;
			}
			item.setReservedBy(user);
			return update(item);
		} catch (HibernateException e) {
			throw new DaoException(e.getMessage());
		}

	}
	@Override
	public List<FanItem> getReserved(String username) throws DaoException {
		try {
			List<FanItem> items = getSessionFactory()
									.getCurrentSession()
									.createCriteria(FanItem.class)
									.createAlias("reservedBy", "user")
									.add(Restrictions.eq("user.username", username))
									.list();
			return items;
		} catch (Exception e) {
			throw new DaoException(e.getMessage());
		}
	}
}
