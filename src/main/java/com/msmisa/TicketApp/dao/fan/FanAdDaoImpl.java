package com.msmisa.TicketApp.dao.fan;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.Bid;
import com.msmisa.TicketApp.beans.FanAd;
import com.msmisa.TicketApp.beans.User;
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
	public List<FanAd> getFromMyZone(String adminUsername) throws DaoException {
		// TODO Auto-generated method stub
		try{
			return getSessionFactory().getCurrentSession()
					.createCriteria(FanAd.class)
					.createAlias("fanZone", "fz")
					.createAlias("fz.admin", "fza")
					.add(Restrictions.eq("fza.username", adminUsername))
					.add(Restrictions.le("expirationDate", new Date()))
					.list();
		} catch(HibernateException e){
			throw new DaoException(e.getMessage());
		}
	}


	@Override
	public List<FanAd> getByZone(Integer zoneID) throws DaoException {
		// TODO Auto-generated method stub
		try {
			return getSessionFactory()
					.getCurrentSession()
					.createCriteria(FanAd.class)
					.createAlias("fanZone", "fz")
					.add(Restrictions.eq("fz.id", zoneID))
					.add(Restrictions.le("expirationDate", new Date()))
					.list();
		} catch (HibernateException e) {
			throw new DaoException(e.getMessage());
		}
	}


	@Override
	public List<FanAd> getFromUser(String username) throws DaoException {
		// TODO Auto-generated method stub
		try {
			return getSessionFactory()
				.getCurrentSession()
				.createCriteria(FanAd.class)
				.createAlias("postedBy", "user")
				.add(Restrictions.eq("user.username", username))
				.add(Restrictions.le("expirationDate", new Date()))
				.list();
		} catch(Exception e) {
			throw new DaoException(e.getMessage());
		}
	}
	// TODO OPTIMISTIC LOCK
	@Override
	public FanAd acceptBid(Integer bidId, String username) {
		List<FanAd> ads = getSessionFactory()
							.getCurrentSession()
							.createCriteria(FanAd.class)
							.createAlias("bidList", "bid")
							.add(Restrictions.eq("bid.id", bidId))
							.setMaxResults(1)
							.list();
		Bid acceptedBid = getSessionFactory().getCurrentSession().get(Bid.class, bidId);
		FanAd ad = ads.get(0);
		if(ad.getPostedBy().getUsername().equals(username) && acceptedBid != null) {
			ad.setAcceptedBid(acceptedBid);
			ad = update(ad);
		}
		return ad;
	}
	
	@Override
	public FanAd changeAppove(Integer id, boolean approved) throws DaoException {
		FanAd ad = getSessionFactory().getCurrentSession().get(FanAd.class, id);
		getSessionFactory().getCurrentSession().lock(ad, LockMode.OPTIMISTIC);
		ad.setAccepted(approved);
		return update(ad);
	}
	@Override
	public FanAd assignTo(Integer id, String username) throws DaoException {
		FanAd ad = getSessionFactory().getCurrentSession().get(FanAd.class, id);
		if(ad.getAdmin() != null) {
			return ad;
		}
		getSessionFactory().getCurrentSession().lock(ad, LockMode.OPTIMISTIC);
		List<User> users = getSessionFactory()
			.getCurrentSession()
			.createCriteria(User.class)
			.add(Restrictions.eq("username", username))
			.setMaxResults(1)
			.list();
		ad.setAdmin(users.get(0));
		return update(ad);
	}

}
