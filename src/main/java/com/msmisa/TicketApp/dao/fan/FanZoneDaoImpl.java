package com.msmisa.TicketApp.dao.fan;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.msmisa.TicketApp.beans.FanZone;
import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dao.AbstractGenericDao;
import com.msmisa.TicketApp.dao.DaoException;

@Transactional
@Repository
public class FanZoneDaoImpl extends AbstractGenericDao<FanZone, Integer> implements FanZoneDao {
	@Autowired
	public FanZoneDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Set<User> getAdmins(Integer zoneID) throws DaoException {
		// TODO Auto-generated method stub
		FanZone zone = get(zoneID);
		Hibernate.initialize(zone.getAdmin());
		Set<User> admins = zone.getAdmin();
		return admins;
	}
	@Override
	public List<FanZone> getMyZones(String usernameAdmin) throws DaoException {
		// TODO Auto-generated method stub
		try {
			return getSessionFactory()
			.getCurrentSession()
			.createCriteria(FanZone.class)
			.createAlias("admin", "a")
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
			.add(Restrictions.eq("a.username", usernameAdmin))
			.list();
		} catch(Exception e) {
			throw new DaoException(e.getMessage());
		}
	}
	
}
