package com.msmisa.TicketApp.dao.fan;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.FanAd;
import com.msmisa.TicketApp.dao.AbstractGenericDao;
import com.msmisa.TicketApp.dao.DaoException;

@Repository
@Transactional
public class FanAdDaoImpl extends AbstractGenericDao<FanAd, Integer> implements FanAdDao {

	@Autowired
	public FanAdDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}


	@Override
	public List<FanAd> getMyToApprove(String username) throws DaoException {
		// TODO Auto-generated method stub
		try{
			return getSessionFactory().getCurrentSession()
					.createCriteria(FanAd.class)
					.createAlias("admin", "a")
					.add(Restrictions.eq("a.username", username))
					.list();
		} catch(HibernateException e){
			throw new DaoException(e.getMessage());
		}
	}


	@Override
	public List<FanAd> getFromMyZone(String username) throws DaoException {
		// TODO Auto-generated method stub
		try{
			return getSessionFactory().getCurrentSession()
					.createCriteria(FanAd.class)
					.createAlias("fanZone", "fz")
					.createAlias("fz.admin", "fza")
					.add(Restrictions.eq("fza.username", username))
					.list();
		} catch(HibernateException e){
			throw new DaoException(e.getMessage());
		}
	}
	

}
